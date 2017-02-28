package jp.co.tukiyo.twitter.viewmodel

import android.content.Context
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class FragmentViewModel(override val context: Context) : BaseViewModel(), LifecycleProvider<FragmentEvent> {

    val lifeCycleEvent: BehaviorSubject<FragmentEvent> = BehaviorSubject.create()

    override fun lifecycle(): Observable<FragmentEvent> = lifeCycleEvent.hide()

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindFragment(lifeCycleEvent)
    }

    override fun <T : Any?> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifeCycleEvent, event)
    }

}