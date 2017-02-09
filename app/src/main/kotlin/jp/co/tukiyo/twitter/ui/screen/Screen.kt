package jp.co.tukiyo.twitter.ui.screen

import jp.co.tukiyo.twitter.ui.fragment.BaseFragment


interface Screen {
    val identify: String
    val fragment: BaseFragment
        get() = fragmentFactory.invoke()
    val fragmentFactory: () -> BaseFragment
}