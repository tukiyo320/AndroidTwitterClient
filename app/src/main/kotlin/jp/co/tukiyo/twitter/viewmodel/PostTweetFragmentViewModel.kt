package jp.co.tukiyo.twitter.viewmodel

import android.content.Context
import android.widget.Toast
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.subjects.BehaviorSubject

class PostTweetFragmentViewModel(context: Context) : FragmentViewModel(context) {
    val subject : BehaviorSubject<Tweet> = BehaviorSubject.create()

    fun postTweet(text: String?) {
        text?.let {
            Twitter.getApiClient().statusesService.update(it, null, false, null, null, null, false, false, null)
                    .enqueue(object : Callback<Tweet>() {
                        override fun success(result: Result<Tweet>?) {
                            Toast.makeText(context, "tweeted!!", Toast.LENGTH_SHORT).show()
                            result?.run { subject.onNext(data) }
                            subject.onComplete()
                        }

                        override fun failure(exception: TwitterException?) {
                            Toast.makeText(context, "tweet failed", Toast.LENGTH_SHORT).show()
                            exception?.run { subject.onError(this) }
                            subject.onComplete()
                        }
                    })
        }
    }
}