package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Observable
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.databinding.FragmentTimelineBinding
import jp.co.tukiyo.twitter.extensions.onNext
import jp.co.tukiyo.twitter.extensions.sync
import jp.co.tukiyo.twitter.ui.adapter.TweetListAdapter
import jp.co.tukiyo.twitter.ui.listener.OnRecyclerViewListener
import jp.co.tukiyo.twitter.ui.screen.PostTweetScreen
import jp.co.tukiyo.twitter.viewmodel.TimelineFragmentViewModel

@FragmentWithArgs
class TimelineFragment : BaseFragment<FragmentTimelineBinding>(), OnRecyclerViewListener {
    override val layoutResourceId: Int = R.layout.fragment_timeline
    lateinit var viewModel: TimelineFragmentViewModel
    lateinit var tweetListAdapter : TweetListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = TimelineFragmentViewModel(context, savedInstanceState)
        tweetListAdapter = TweetListAdapter(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            tweetList.run {
                layoutManager = LinearLayoutManager(activity)
                adapter = tweetListAdapter
            }
            swipeTweetList.run {
                setOnRefreshListener {
                    viewModel.fetchTimeline()
                    if (isRefreshing) {
                        isRefreshing = false
                    }
                }
            }
        }

        viewModel.tweets.sync()
                .onNext { tweetListAdapter.add(0, it) }
                .subscribe()
                .run {
                    disposables?.add(this)
                }

        viewModel.fetchTimeline()
    }

    override fun onRecyclerViewListener(v: View, position: Int) {

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        viewModel.destroy(outState)
        super.onSaveInstanceState(outState)
    }
}
