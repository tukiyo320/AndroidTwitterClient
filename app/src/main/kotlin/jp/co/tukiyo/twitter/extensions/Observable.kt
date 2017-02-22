package jp.co.tukiyo.twitter.extensions

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

fun <T> Observable<T>.async(scheduler: Scheduler): Observable<T> {
    return subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.sync(): Observable<T> {
    return subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread())
}
fun <T> Observable<T>.onError(block : (Throwable) -> Unit) : Subscription<T> {
    return Subscription(this).onError(block)
}

fun <T> Observable<T>.onCompleted(block: () -> Unit) : Subscription<T> {
    return Subscription(this).onCompleted(block)
}

fun <T> Observable<T>.onNext(block: (T) -> Unit) : Subscription<T> {
    return Subscription(this).onNext(block)
}

open class Subscription<T>(val observable: Observable<T>) {
    private var error: (Throwable) -> Unit = {throw it}
    private var completed: () -> Unit = {}
    private var next: (T) -> Unit = {}

    fun onError(block: (Throwable) -> Unit) : Subscription<T> {
        error = block
        return this
    }

    fun onCompleted(block: () -> Unit) : Subscription<T> {
        completed = block
        return this
    }

    fun onNext(block: (T) -> Unit) : Subscription<T> {
        next = block
        return this
    }

    fun subscribe(): Disposable = observable.subscribe({
        next.invoke(it)
    },{
        it?.let {error.invoke(it)}
    },{
        completed.invoke()
    })
}
