package jp.co.tukiyo.twitter.application

import android.content.Context
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import io.fabric.sdk.android.Fabric
import jp.co.tukiyo.twitter.R
import android.app.Application as AndroidApplication

class Application : AndroidApplication() {

    companion object {
        fun from(context: Context): Application {
            return context.applicationContext as Application
        }
    }

    override fun onCreate() {
        super.onCreate()
        val twitterKey = resources.getString(R.string.twitter_key)
        val twitterSecret = resources.getString(R.string.twitter_secret)
        val authConfig = TwitterAuthConfig(twitterKey, twitterSecret)
        Fabric.with(this, Twitter(authConfig))
    }
}