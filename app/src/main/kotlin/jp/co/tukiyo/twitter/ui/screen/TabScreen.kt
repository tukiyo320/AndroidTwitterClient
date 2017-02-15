package jp.co.tukiyo.twitter.ui.screen

import jp.co.tukiyo.twitter.ui.fragment.BaseFragment
import jp.co.tukiyo.twitter.ui.fragment.TabFragmentBuilder

class TabScreen : Screen {
    override val identify: String = TabScreen::class.java.name
    override val fragmentFactory: () -> BaseFragment = { -> TabFragmentBuilder().build() }
}