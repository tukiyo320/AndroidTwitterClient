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
