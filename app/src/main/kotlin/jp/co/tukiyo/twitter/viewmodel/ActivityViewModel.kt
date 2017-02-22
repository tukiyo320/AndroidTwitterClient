package jp.co.tukiyo.twitter.viewmodel

import android.content.Context
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class ActivityViewModel(override val context: Context) : BaseViewModel(), LifecycleProvider<ActivityEvent> {

    val lifeCycleEvent: BehaviorSubject<ActivityEvent> = BehaviorSubject.create()

    override fun lifecycle(): Observable<ActivityEvent> = lifeCycleEvent.hide()

    override fun <T : Any?> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifeCycleEvent, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifeCycleEvent)
    }
}