package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.astuetz.PagerSlidingTabStrip
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.ui.adapter.TabAdapter
import jp.co.tukiyo.twitter.ui.screen.PostTweetScreen

@FragmentWithArgs
class TabFragment : BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_tab
    lateinit var adapter : FragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TabAdapter(childFragmentManager)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pager : ViewPager = view?.findViewById(R.id.pager) as ViewPager
        pager.adapter = adapter

        val tab : PagerSlidingTabStrip = view?.findViewById(R.id.tab) as PagerSlidingTabStrip
        tab.setViewPager(pager)

        (view?.findViewById(R.id.tweet_button) as FloatingActionButton).run {
            setOnClickListener {
                mainActivity.pushScreen(PostTweetScreen())
            }
        }
    }
}
