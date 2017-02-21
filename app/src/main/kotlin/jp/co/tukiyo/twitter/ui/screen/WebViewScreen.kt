package jp.co.tukiyo.twitter.ui.screen

import android.support.v4.app.Fragment
import jp.co.tukiyo.twitter.ui.fragment.WebViewFragmentBuilder

class WebViewScreen : Screen {
    override val identify: String = WebViewScreen::class.java.name
    override val fragmentFactory: () -> Fragment = { -> WebViewFragmentBuilder().build() }
}