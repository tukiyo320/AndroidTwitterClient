package jp.co.tukiyo.twitter.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.widget.*
import com.astuetz.PagerSlidingTabStrip
import com.bumptech.glide.Glide
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.extensions.glide.BitmapViewBackgroundTarget
import jp.co.tukiyo.twitter.extensions.onNext
import jp.co.tukiyo.twitter.extensions.sync
import jp.co.tukiyo.twitter.ui.adapter.TabAdapter
import jp.co.tukiyo.twitter.ui.screen.PostTweetScreen
import jp.co.tukiyo.twitter.viewmodel.TabFragmentViewModel

@FragmentWithArgs
class TabFragment : BaseFragment() {
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

        val drawer: DrawerLayout = view?.findViewById(R.id.top_navigation) as DrawerLayout
        val navigationList: ListView = view?.findViewById(R.id.top_left_navigation_list) as ListView

        navigationList.adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listOf("profile", "config"))

        val pager: ViewPager = view?.findViewById(R.id.pager) as ViewPager
        pager.adapter = adapter

        val tab: PagerSlidingTabStrip = view?.findViewById(R.id.tab) as PagerSlidingTabStrip
        tab.setViewPager(pager)

        val navigationHeader: RelativeLayout = view?.findViewById(R.id.top_left_navigation_header) as RelativeLayout
        val navigationHeaderIcon: ImageView = view?.findViewById(R.id.top_left_navigation_header_icon) as ImageView
        val navigationHeaderUserName: TextView = view?.findViewById(R.id.top_left_navigation_header_username) as TextView

        (view?.findViewById(R.id.tweet_button) as FloatingActionButton).run {
            setOnClickListener {
                mainActivity.pushScreen(PostTweetScreen())
            }
        }

        viewModel.user.sync()
                .onNext {
                    navigationHeaderUserName.text = it.name
                    Glide.with(context)
                            .load(it.profileBackgroundImageUrl)
                            .asBitmap()
                            .centerCrop()
                            .placeholder(android.R.drawable.ic_menu_call)
                            .dontAnimate()
                            .into(BitmapViewBackgroundTarget(navigationHeader))
                    Glide.with(context)
                            .load(it.profileImageUrl)
                            .placeholder(android.R.drawable.ic_menu_call)
                            .dontAnimate()
                            .into(navigationHeaderIcon)
                }
                .subscribe()
                .run { disposables?.add(this) }

        viewModel.fetchUserInfo()
    }
}
