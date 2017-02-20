package jp.co.tukiyo.twitter.ui.screen

import android.support.v4.app.Fragment
import jp.co.tukiyo.twitter.ui.fragment.TimelineFragmentBuilder

class TimelineScreen : Screen{
    override val identify : String = TimelineScreen::class.java.name
    override val fragmentFactory: () -> Fragment = { -> TimelineFragmentBuilder().build() }
}