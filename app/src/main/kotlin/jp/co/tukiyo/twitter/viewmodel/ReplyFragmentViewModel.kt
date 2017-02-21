package jp.co.tukiyo.twitter.viewmodel

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.subjects.BehaviorSubject

class ReplyFragmentViewModel(context: Context, savedInstanceState: Bundle?) : FragmentViewModel(context) {
    val replies : BehaviorSubject<Tweet> = BehaviorSubject.create()
    var latestReplyId: Long?

    init {
        val id = savedInstanceState?.getLong("latest_reply_id")
        latestReplyId = id?.let {
            return@let if(it == 0L) {
                null
            } else {
                it
            }
        }
    }

    fun fetchReply() {
        val sinceId = latestReplyId?.let { it + 1 }
        Twitter.getApiClient().statusesService.mentionsTimeline(null, sinceId, null, false, false, false)
                .enqueue(object : Callback<List<Tweet>>() {
                    override fun failure(exception: TwitterException?) {
                        Toast.makeText(context, "failed get replies", Toast.LENGTH_SHORT).show()
                    }

                    override fun success(result: Result<List<Tweet>>?) {
                        result?.data?.run {
                            reversed().forEach { replies.onNext(it) }
                            firstOrNull()?.let {
                                latestReplyId = it.id
                            }
                        }
                    }
                })
    }

    fun destroy(outState: Bundle?) {
        latestReplyId?.let {
            outState?.putLong("latest_reply_id", it)
        }
    }
}