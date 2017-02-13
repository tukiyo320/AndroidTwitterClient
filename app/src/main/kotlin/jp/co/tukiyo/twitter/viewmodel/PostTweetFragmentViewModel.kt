package jp.co.tukiyo.twitter.viewmodel

import android.content.Context
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.models.Tweet
import retrofit2.Call

class PostTweetFragmentViewModel(context: Context) : FragmentViewModel(context) {

    fun postTweet(text: String?): Call<Tweet>? {
        return text?.let {
            Twitter.getApiClient().statusesService.update(it, null, false, null, null, null, false, false, null)
        }
    }
}