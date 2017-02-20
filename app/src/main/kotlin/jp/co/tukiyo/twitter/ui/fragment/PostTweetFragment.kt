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
import jp.co.tukiyo.twitter.databinding.FragmentPostTweetBinding
import jp.co.tukiyo.twitter.extensions.onCompleted
import jp.co.tukiyo.twitter.extensions.sync
import jp.co.tukiyo.twitter.viewmodel.PostTweetFragmentViewModel

@FragmentWithArgs
class PostTweetFragment : BaseFragment<FragmentPostTweetBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_post_tweet

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = PostTweetFragmentViewModel(context)

        binding?.run {
            toolbar.run {
                setNavigationOnClickListener {
                    mainActivity.popScreen()
                }
            }
            tweetPostButton.run {
                setOnClickListener {
                    viewModel.postTweet(tweetNewText?.text?.toString())
                }
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
        binding?.tweetNewText?.text?.clear()
    }
}