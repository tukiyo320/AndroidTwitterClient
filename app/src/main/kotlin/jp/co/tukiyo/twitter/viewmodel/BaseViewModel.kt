package jp.co.tukiyo.twitter.viewmodel

import android.os.Bundle
import com.evernote.android.state.StateSaver
import io.reactivex.disposables.CompositeDisposable
import jp.co.tukiyo.twitter.application.Application

abstract class BaseViewModel : ViewModel {
    override val disposables: CompositeDisposable = CompositeDisposable()
    val application: Application
        get() = Application.from(context)

    fun saveInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            StateSaver.saveInstanceState(this, it)
        }
    }

    fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            StateSaver.restoreInstanceState(this, it)
        }
    }
}