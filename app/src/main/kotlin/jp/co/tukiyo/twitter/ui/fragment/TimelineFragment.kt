package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.databinding.FragmentTimelineBinding
import jp.co.tukiyo.twitter.extensions.onNext
import jp.co.tukiyo.twitter.extensions.sync
import jp.co.tukiyo.twitter.ui.adapter.TweetListAdapter
import jp.co.tukiyo.twitter.ui.listener.OnRecyclerViewListener
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
                .bindToLifecycle(this)
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
