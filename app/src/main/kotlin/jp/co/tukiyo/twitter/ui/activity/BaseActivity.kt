package jp.co.tukiyo.twitter.ui.activity

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<BINDING: ViewDataBinding>: AppCompatActivity() {

    abstract val layoutResourceId: Int
    lateinit  var binding: BINDING
    var disposables: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposables = CompositeDisposable()
        binding = DataBindingUtil.setContentView(this, layoutResourceId)
    }

    override fun onDestroy() {
        disposables?.dispose()
        super.onDestroy()
    }
}