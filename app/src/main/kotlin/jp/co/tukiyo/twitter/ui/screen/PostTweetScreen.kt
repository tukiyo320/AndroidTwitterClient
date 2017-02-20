package jp.co.tukiyo.twitter.ui.screen

import android.support.v4.app.Fragment
import jp.co.tukiyo.twitter.ui.fragment.PostTweetFragmentBuilder

class PostTweetScreen : Screen {
    override val identify: String = PostTweetScreen::class.java.name
    override val fragmentFactory: () -> Fragment = { -> PostTweetFragmentBuilder().build() }
}