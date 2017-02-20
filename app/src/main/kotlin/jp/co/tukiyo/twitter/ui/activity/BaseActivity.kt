package jp.co.tukiyo.twitter.ui.activity

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<BINDING: ViewDataBinding>: AppCompatActivity() {

    abstract val layoutResourceId: Int
    lateinit  var binding: BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResourceId)
    }
}