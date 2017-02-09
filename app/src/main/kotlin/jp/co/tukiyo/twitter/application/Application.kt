package jp.co.tukiyo.twitter.application

import android.content.Context
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import io.fabric.sdk.android.Fabric
import android.app.Application as AndroidApplication

class Application : AndroidApplication() {

    private val TWITTER_KEY = ""
    private val TWITTER_SECRET = ""

    companion object {
        fun from(context: Context): Application {
            return context.applicationContext as Application
        }
    }

    override fun onCreate() {
        super.onCreate()
        val authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)
        Fabric.with(this, Twitter(authConfig))
    }
}