package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Observable
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.databinding.FragmentMyMediaTimelineBinding
import jp.co.tukiyo.twitter.extensions.onNext
import jp.co.tukiyo.twitter.extensions.sync
import jp.co.tukiyo.twitter.ui.adapter.MediaAdapter
import jp.co.tukiyo.twitter.viewmodel.MyMediaTimelineFragmentViewModel

@FragmentWithArgs
class MyMediaTimelineFragment : BaseFragment<FragmentMyMediaTimelineBinding>(), AbsListView.OnScrollListener {
    override val layoutResourceId: Int = R.layout.fragment_my_media_timeline
    lateinit var viewModel: MyMediaTimelineFragmentViewModel
    lateinit var mediaAdapter: MediaAdapter
    lateinit var tweetStream: Observable<List<Tweet>>
    var isLoading: Boolean = false
    var previousTotal: Int = 0
    var visibleThreshold = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MyMediaTimelineFragmentViewModel(context).apply {
            restoreInstanceState(savedInstanceState)
            tweetStream = searchResults.sync()
                    .bindToLifecycle(this)
                    .filter { it.isNotEmpty() }
                    .share()
        }
        mediaAdapter = MediaAdapter(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tweetStream
                .filter { results ->
                    mediaAdapter.getTweetOfFirstItem()?.let { first ->
                        results.last().id > first.id
                    } ?: true
                }
                .flatMap { Observable.fromIterable(it.reversed()) }
                .onNext { mediaAdapter.add(0, it) }
                .subscribe()
                .run { disposables?.add(this) }

        tweetStream
                .filter { results ->
                    mediaAdapter.getTweetOfLastItem()?.let { last ->
                        results.first().id < last.id
                    } ?: false
                }
                .flatMap { Observable.fromIterable(it) }
                .onNext { mediaAdapter.add(it) }
                .subscribe()
                .run { disposables?.add(this) }

        binding?.run {
            mediaGrid.run {
                adapter = mediaAdapter
                setOnScrollListener(this@MyMediaTimelineFragment)
            }
            swipeTweetList.run {
                setOnRefreshListener {
                    if (isRefreshing) {
                        isRefreshing = false
                    }
                    viewModel.fetchMyMediaRecent()
                }
            }

        }
    }

    override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false
                previousTotal = totalItemCount
            }
        }
        if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            viewModel.fetchMyMediaPast()
            isLoading = true
        }
    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        viewModel.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
}