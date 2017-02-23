package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.databinding.FragmentTabBinding
import jp.co.tukiyo.twitter.ui.adapter.TabAdapter
import jp.co.tukiyo.twitter.ui.screen.PostTweetScreen
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
            pager?.adapter = adapter
            tab?.setViewPager(binding?.pager)
            tweetButton?.setOnClickListener {
                mainActivity.pushScreen(PostTweetScreen())
            }
            mainActivity.setToolbar(toolbar)
        }
    }
}
