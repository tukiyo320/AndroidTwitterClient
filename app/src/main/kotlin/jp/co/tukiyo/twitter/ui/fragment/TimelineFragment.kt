package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.extensions.sync
import jp.co.tukiyo.twitter.ui.adapter.TweetListAdapter
import jp.co.tukiyo.twitter.ui.listener.OnRecyclerViewListener
import jp.co.tukiyo.twitter.viewmodel.TimelineFragmentViewModel

@FragmentWithArgs
class TimelineFragment : BaseFragment(), OnRecyclerViewListener {
    override val layoutResourceId: Int = R.layout.fragment_timeline

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_timeline, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = TimelineFragmentViewModel(context)

        val tweetListAdapter = TweetListAdapter(context)

        val recyclerView = (view?.findViewById(R.id.tweet_list) as RecyclerView).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = tweetListAdapter
        }

        viewModel.tweets.sync().subscribe {
            recyclerView.adapter
            tweetListAdapter.clear()
            tweetListAdapter.addAll(it)
        }.run { disposables?.add(this) }

        viewModel.fetchTimeline()
    }

    override fun onRecyclerViewListener(v: View, position: Int) {

    }

}
