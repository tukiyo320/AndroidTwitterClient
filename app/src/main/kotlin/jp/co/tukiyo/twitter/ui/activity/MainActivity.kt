package jp.co.tukiyo.twitter.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.databinding.ActivityMainBinding
import jp.co.tukiyo.twitter.extensions.glide.BitmapViewBackgroundTarget
import jp.co.tukiyo.twitter.extensions.onNext
import jp.co.tukiyo.twitter.extensions.sync
import jp.co.tukiyo.twitter.ui.screen.Screen
import jp.co.tukiyo.twitter.ui.screen.TabScreen
import jp.co.tukiyo.twitter.ui.screen.WebViewScreen
import jp.co.tukiyo.twitter.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutResourceId: Int = R.layout.activity_main
    val viewModel: MainActivityViewModel = MainActivityViewModel(this)

    fun pushScreen(screen: Screen) {
        if (supportFragmentManager.findFragmentByTag(screen.identify) != null) return

        supportFragmentManager.beginTransaction()
                .replace(R.id.container_screen, screen.fragment, screen.identify)
                .addToBackStack(screen.identify)
                .commit()
        supportFragmentManager.executePendingTransactions()
    }

    fun popScreen() {
        if (supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStackImmediate()
    }

    fun replaceScreen(screen: Screen) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container_screen, screen.fragment, screen.identify)
                .commitNow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.run {
            topLeftNavigationList?.run {
                adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listOf("profile", "config", "official"))
                setOnItemClickListener { adapterView, view, i, l ->
                    when(i) {
                        2 -> pushScreen(WebViewScreen())
                    }
                }
            }
        }

        viewModel.user.sync()
                .onNext {
                    binding.run {
                        user = it
                        Glide.with(this@MainActivity)
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

        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            1001 -> {
                viewModel.fetchUserInfo()
                replaceScreen(TabScreen())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
