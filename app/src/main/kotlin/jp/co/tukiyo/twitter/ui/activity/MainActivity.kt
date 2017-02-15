package jp.co.tukiyo.twitter.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.ui.screen.Screen
import jp.co.tukiyo.twitter.ui.screen.TabScreen
import jp.co.tukiyo.twitter.ui.screen.TimelineScreen

class MainActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_main)

        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            1001 -> {
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
