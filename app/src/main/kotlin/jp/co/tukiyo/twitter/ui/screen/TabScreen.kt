package jp.co.tukiyo.twitter.ui.screen

import android.support.v4.app.Fragment
import jp.co.tukiyo.twitter.ui.fragment.TabFragmentBuilder

class TabScreen : Screen {
    override val identify: String = TabScreen::class.java.name
    override val fragmentFactory: () -> Fragment = { -> TabFragmentBuilder().build() }
}