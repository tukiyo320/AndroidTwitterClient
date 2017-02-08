package jp.co.tukiyo.twitter

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import io.fabric.sdk.android.Fabric

class MainActivity : AppCompatActivity() {
    lateinit private var loginButton: TwitterLoginButton

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private val TWITTER_KEY = "69pykGMynw8UslHazQ4KV3sQD"
    private val TWITTER_SECRET = "98EpKy4eSqQFqVe8gJBPKGfe7MZgdDN1W3hoilFKdLU7D1LOPb"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)
        Fabric.with(this, Twitter(authConfig))

        loginButton = findViewById(R.id.twitter_login_button) as TwitterLoginButton
        loginButton.isEnabled = true
        loginButton.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                val session = result.data
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                val msg = "@" + session.userName + " logged in! (#" + session.userId + ")"
                Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
            }

            override fun failure(exception: TwitterException) {
                Log.d("TwitterKit", "Login with Twitter failure", exception)
            }
        }


        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data)
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
