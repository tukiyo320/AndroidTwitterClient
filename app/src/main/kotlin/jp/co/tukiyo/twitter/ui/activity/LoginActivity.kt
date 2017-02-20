package jp.co.tukiyo.twitter.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.twitter.sdk.android.core.models.User
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val layoutResourceId: Int = R.layout.activity_login
    lateinit private var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("jp.co.tukiyo.twitter.settings", Context.MODE_PRIVATE)

        binding.twitterLoginButton.run {
            isEnabled = true
            callback = object : Callback<TwitterSession>() {
                override fun success(result: Result<TwitterSession>) {
                    result.data.run {
                        setUserData(authToken, userName, userId)
                    }
                }

                override fun failure(exception: TwitterException) {
                    toast("twitter login failed")
                    Log.d("TwitterKit", "Login with Twitter failure", exception)
                }
            }
        }

        loadUser()
    }

    fun loadUser() {
        val token = prefs.getString("token", "")
        val secret = prefs.getString("secret", "")
        val name = prefs.getString("name", "")
        val id = prefs.getLong("id", 0L)

        val authToken = TwitterAuthToken(token, secret)
        setUserData(authToken, name, id)
    }

    fun setUserData(authToken: TwitterAuthToken, name: String, id: Long) {
        val session = TwitterSession(authToken, id, name)
        Twitter.getSessionManager().setSession(id, session)
        Twitter.getSessionManager().activeSession = session
        val userCall = Twitter.getApiClient().accountService.verifyCredentials(true, false)

        userCall.enqueue(object : Callback<User>() {
            override fun failure(exception: TwitterException?) {
                binding.twitterLoginButton.callOnClick()
            }

            override fun success(result: Result<User>?) {
                val user = result?.data!!
                prefs.edit().apply {
                    putLong("id", user.id)
                    putString("name", user.name)
                    putString("token", authToken.token)
                    putString("secret", authToken.secret)
                }.apply()
                setResult(Activity.RESULT_OK)
                finish()
            }
        })
    }

    fun toast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        binding.twitterLoginButton.onActivityResult(requestCode, resultCode, data)
    }
}
