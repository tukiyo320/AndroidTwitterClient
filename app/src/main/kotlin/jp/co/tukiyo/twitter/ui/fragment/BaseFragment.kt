package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import jp.co.tukiyo.twitter.application.Application
import jp.co.tukiyo.twitter.ui.activity.MainActivity

abstract class BaseFragment : Fragment() {

    abstract val layoutResourceId: Int
    var disposables: CompositeDisposable? = null
    val application: Application
        get() = Application.from(context)
    val mainActivity: MainActivity
        get() = activity as MainActivity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        disposables = CompositeDisposable()
        return inflater?.inflate(layoutResourceId, container, false)
    }

    override fun onDestroyView() {
        disposables?.dispose()
        super.onDestroyView()
    }
}