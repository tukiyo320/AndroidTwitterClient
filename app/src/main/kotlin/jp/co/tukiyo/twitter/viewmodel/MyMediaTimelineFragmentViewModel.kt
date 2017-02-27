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
import io.reactivex.subjects.Subject

class MyMediaTimelineFragmentViewModel(context: Context) : FragmentViewModel(context) {
    val searchResults: BehaviorSubject<List<Tweet>> = BehaviorSubject.create()

    @State
    var latestTweetId: Long? = null

    @State
    var oldestTweetId: Long? = null

    fun fetchMyMediaRecent() {
        fetchMyMediaTweets(searchResults, latestTweetId, null)
    }

    fun fetchMyMediaPast() {
        fetchMyMediaTweets(searchResults, null, oldestTweetId?.let { it - 1 })
    }

    fun fetchMyMediaTweets(subject: Subject<List<Tweet>>, sinceId: Long?, maxId: Long?) {
        Twitter.getApiClient().accountService.verifyCredentials(true,false)
                .enqueue(object : Callback<User>() {
                    override fun failure(exception: TwitterException?) {
                        Toast.makeText(context, "failed get user info", Toast.LENGTH_SHORT).show()
                        searchResults.onError(exception)
                    }

                    override fun success(result: Result<User>?) {
                        result?.data?.let { fetchUserMediaTweets(subject, it.screenName, sinceId, maxId) }
                    }
                })
    }

    fun fetchUserMediaTweets(subject: Subject<List<Tweet>>, screenName: String, sinceId: Long?, maxId: Long?) {
        Twitter.getApiClient().searchService.tweets("filter:images from:$screenName exclude:retweets", null, null, null, null, 6, null, sinceId, maxId, true)
                .enqueue(object : Callback<Search>() {
                    override fun success(result: Result<Search>?) {
                        result?.data?.run {
                            subject.onNext(tweets)
                            latestTweetId = tweets.firstOrNull { tweet ->
                                latestTweetId?.let { it < tweet.id } ?: true
                            }?.id ?: latestTweetId
                            oldestTweetId = tweets.lastOrNull { tweet ->
                                oldestTweetId?.let { it > tweet.id } ?: true
                            }?.id ?: oldestTweetId
                        }
                    }

                    override fun failure(exception: TwitterException?) {
                        Toast.makeText(context, "failed media tweet", Toast.LENGTH_SHORT).show()
                    }

                })
    }
}