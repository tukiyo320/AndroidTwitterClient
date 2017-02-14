package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.extensions.onCompleted
import jp.co.tukiyo.twitter.extensions.onNext
import jp.co.tukiyo.twitter.extensions.sync
import jp.co.tukiyo.twitter.viewmodel.PostTweetFragmentViewModel

@FragmentWithArgs
class PostTweetFragment : BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_post_tweet
    var tweetText : EditText? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_post_tweet, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = PostTweetFragmentViewModel(context)

        val toolbar = (view?.findViewById(R.id.toolbar) as Toolbar?)?.apply {
            setNavigationOnClickListener {
                mainActivity.popScreen()
            }
        }

        tweetText = view?.findViewById(R.id.tweet_new_text) as EditText?

        val fab = (view?.findViewById(R.id.tweet_post_button) as FloatingActionButton).apply {
            setOnClickListener {
                viewModel.postTweet(tweetText?.text?.toString())
            }
        }

        viewModel.subject.sync()
                .onCompleted {
                    reset()
                    mainActivity.popScreen()
                }
                .subscribe()
                .run { disposables?.add(this) }
    }

    fun reset() {
        tweetText?.text?.clear()
    }
}