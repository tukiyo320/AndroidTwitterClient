package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.databinding.FragmentMyMediaTimelineBinding
import jp.co.tukiyo.twitter.extensions.onNext
import jp.co.tukiyo.twitter.extensions.sync
import jp.co.tukiyo.twitter.ui.adapter.MediaAdapter
import jp.co.tukiyo.twitter.viewmodel.MyMediaTimelineFragmentViewModel

@FragmentWithArgs
class MyMediaTimelineFragment : BaseFragment<FragmentMyMediaTimelineBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_my_media_timeline
    lateinit var viewModel: MyMediaTimelineFragmentViewModel
    lateinit var mediaAdapter: MediaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MyMediaTimelineFragmentViewModel(context).apply {
            restoreInstanceState(savedInstanceState)
            mediaTweets.sync()
                    .bindToLifecycle(this)
                    .flatMap { Observable.fromIterable(it.extendedEtities.media) }
                    .onNext { mediaAdapter.add(0, it) }
                    .subscribe()
                    .run { disposables.add(this) }
        }
        mediaAdapter = MediaAdapter(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            mediaGrid.adapter = mediaAdapter
        }

        viewModel.fetchMyMediaTweets()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        viewModel.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
}