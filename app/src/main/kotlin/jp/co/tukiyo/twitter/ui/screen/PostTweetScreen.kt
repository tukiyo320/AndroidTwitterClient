package jp.co.tukiyo.twitter.ui.screen

import jp.co.tukiyo.twitter.ui.fragment.BaseFragment
import jp.co.tukiyo.twitter.ui.fragment.PostTweetFragmentBuilder

class PostTweetScreen : Screen {
    override val identify: String = PostTweetScreen::class.java.name
    override val fragmentFactory: () -> BaseFragment = { -> PostTweetFragmentBuilder().build() }
}