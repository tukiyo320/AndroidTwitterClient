package jp.co.tukiyo.twitter.viewmodel

import io.reactivex.disposables.CompositeDisposable
import jp.co.tukiyo.twitter.application.Application

abstract class BaseViewModel : ViewModel {
    override val disposables: CompositeDisposable = CompositeDisposable()
    val application: Application
        get() = Application.from(context)
}