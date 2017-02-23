package jp.co.tukiyo.twitter.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import jp.co.tukiyo.twitter.ui.fragment.MyMediaTimelineFragment
import jp.co.tukiyo.twitter.ui.fragment.ReplyFragment
import jp.co.tukiyo.twitter.ui.fragment.TimelineFragment

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val TITLES : Array<String> = arrayOf("timeline", "reply", "my media")

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> TimelineFragment()
            1 -> ReplyFragment()
            2 -> MyMediaTimelineFragment()
            else -> null!!
        }
    }

    override fun getCount(): Int {
        return TITLES.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TITLES[position]
    }
}