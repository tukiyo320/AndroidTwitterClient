package jp.co.tukiyo.twitter.viewmodel

import android.content.Context
import android.widget.Toast
import com.evernote.android.state.State
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import io.reactivex.subjects.BehaviorSubject

class MyMediaTimelineFragmentViewModel(context: Context) : FragmentViewModel(context) {
    val mediaTweets: BehaviorSubject<Tweet> = BehaviorSubject.create()

    @State
    var latestTweetId: Long? = null

    fun fetchMyMediaTweets() {
        Twitter.getApiClient().accountService.verifyCredentials(true,false)
                .enqueue(object : Callback<User>() {
                    override fun failure(exception: TwitterException?) {
                        Toast.makeText(context, "failed get user info", Toast.LENGTH_SHORT).show()
                        mediaTweets.onError(exception)
                    }

                    override fun success(result: Result<User>?) {
                        result?.data?.let { fetchUserMediaTweets(it.screenName) }
                    }
                })
    }

    fun fetchUserMediaTweets(screenName: String) {
        val sinceId = latestTweetId?.let { it + 1 }
        Twitter.getApiClient().searchService.tweets("filter:images from:$screenName exclude:retweets", null, null, null, null, null, null, sinceId, null, true)
                .enqueue(object : Callback<Search>() {
                    override fun success(result: Result<Search>?) {
                        result?.data?.run {
                            tweets.reversed().forEach { mediaTweets.onNext(it) }
                            tweets.firstOrNull()?.let {
                                latestTweetId = it.id
                            }
                        }
                    }

                    override fun failure(exception: TwitterException?) {
                        Toast.makeText(context, "failed media tweet", Toast.LENGTH_SHORT).show()
                    }

                })
    }
}