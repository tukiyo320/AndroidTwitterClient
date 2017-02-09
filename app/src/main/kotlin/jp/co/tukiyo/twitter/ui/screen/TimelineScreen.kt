package jp.co.tukiyo.twitter.ui.screen

import jp.co.tukiyo.twitter.ui.fragment.BaseFragment
import jp.co.tukiyo.twitter.ui.fragment.TimelineFragmentBuilder

class TimelineScreen : Screen{
    override val identify : String = TimelineScreen::class.java.name
    override val fragmentFactory: () -> BaseFragment = { -> TimelineFragmentBuilder().build() }
}