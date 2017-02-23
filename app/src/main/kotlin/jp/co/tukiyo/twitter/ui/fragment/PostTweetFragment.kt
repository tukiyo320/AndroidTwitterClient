package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
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

        mainActivity.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        val viewModel = PostTweetFragmentViewModel(context)

        binding?.run {
            toolbar.run {
                setNavigationOnClickListener {
                    mainActivity.popScreen()
                }
                mainActivity.setToolbar(this, true)
            }
            tweetPostButton.run {
                setOnClickListener {
                    viewModel.postTweet(tweetNewText?.text?.toString())
                }
            }
        }

        viewModel.subject.sync()
                .bindToLifecycle(this)
                .onCompleted {
                    reset()
                    mainActivity.popScreen()
                }
                .subscribe()
                .run { disposables?.add(this) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainActivity.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun reset() {
        binding?.tweetNewText?.text?.clear()
    }
}