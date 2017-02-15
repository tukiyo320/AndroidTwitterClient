package jp.co.tukiyo.twitter.viewmodel

import android.content.Context
import android.widget.Toast
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.subjects.BehaviorSubject

class ReplyFragmentViewModel(context: Context) : FragmentViewModel(context) {
    val replies : BehaviorSubject<Tweet> = BehaviorSubject.create()

    fun fetchReply() {
        Twitter.getApiClient().statusesService.mentionsTimeline(null, null, null, false, false, false)
                .enqueue(object : Callback<List<Tweet>>() {
                    override fun failure(exception: TwitterException?) {
                        Toast.makeText(context, "failed get replies", Toast.LENGTH_SHORT).show()
                    }

                    override fun success(result: Result<List<Tweet>>?) {
                        result?.data?.run {
                            reversed().forEach { replies.onNext(it) }
                        }
                    }
                })
    }
}