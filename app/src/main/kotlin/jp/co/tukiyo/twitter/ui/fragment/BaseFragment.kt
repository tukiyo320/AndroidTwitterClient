package jp.co.tukiyo.twitter.ui.fragment

import android.support.v4.app.Fragment
import jp.co.tukiyo.twitter.application.Application
import jp.co.tukiyo.twitter.ui.activity.MainActivity

abstract class BaseFragment : Fragment() {

    abstract val layoutResourceId: Int
    val application: Application
        get() = Application.from(context)
    val mainActivity: MainActivity
        get() = activity as MainActivity

}