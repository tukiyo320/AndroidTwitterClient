package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.makeText
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import jp.co.tukiyo.twitter.R
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
                        ?.enqueue(object : Callback<Tweet>() {
                            override fun success(result: Result<Tweet>?) {
                                makeText(context, "tweeted!!", Toast.LENGTH_SHORT).show()
                                mainActivity.popScreen()
                                reset()
                            }

                            override fun failure(exception: TwitterException?) {
                                makeText(context, "tweet failed", Toast.LENGTH_SHORT).show()
                                exception?.run { Log.d("post", this.message) }
                                mainActivity.popScreen()
                                reset()
                            }
                        })
            }
        }
    }

    fun reset() {
        tweetText?.text?.clear()
    }
}