package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.databinding.FragmentTabBinding
import jp.co.tukiyo.twitter.extensions.glide.BitmapViewBackgroundTarget
import jp.co.tukiyo.twitter.extensions.onNext
import jp.co.tukiyo.twitter.extensions.sync
import jp.co.tukiyo.twitter.ui.adapter.TabAdapter
import jp.co.tukiyo.twitter.ui.screen.PostTweetScreen
import jp.co.tukiyo.twitter.ui.screen.WebViewScreen
import jp.co.tukiyo.twitter.viewmodel.TabFragmentViewModel

@FragmentWithArgs
class TabFragment : BaseFragment<FragmentTabBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_tab
    lateinit var adapter: FragmentPagerAdapter
    lateinit var viewModel: TabFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TabAdapter(childFragmentManager)
        viewModel = TabFragmentViewModel(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            topLeftNavigationList?.run {
                adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listOf("profile", "config", "official"))
                setOnItemClickListener { adapterView, view, i, l ->
                    when(i) {
                        2 -> mainActivity.pushScreen(WebViewScreen())
                    }
                }
            }
            pager?.adapter = adapter
            tab?.setViewPager(binding?.pager)
            tweetButton?.setOnClickListener {
                mainActivity.pushScreen(PostTweetScreen())
            }
        }

        viewModel.user.sync()
                .bindToLifecycle(this)
                .onNext {
                    binding?.run {
                        user = it
                        Glide.with(context)
                                .load(it.profileBackgroundImageUrl)
                                .asBitmap()
                                .centerCrop()
                                .placeholder(android.R.drawable.ic_menu_call)
                                .dontAnimate()
                                .into(BitmapViewBackgroundTarget(topLeftNavigationHeader))
                    }
                }
                .subscribe()
                .run { disposables?.add(this) }

        viewModel.fetchUserInfo()
    }
}
