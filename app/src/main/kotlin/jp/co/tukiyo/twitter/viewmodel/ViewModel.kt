package jp.co.tukiyo.twitter.viewmodel

import android.content.Context
import io.reactivex.disposables.CompositeDisposable

interface ViewModel {
    val context: Context
    val disposables: CompositeDisposable

    fun destroy() {
        disposables.dispose()
    }
}