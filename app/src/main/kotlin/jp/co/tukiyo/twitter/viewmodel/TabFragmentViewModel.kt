package jp.co.tukiyo.twitter.viewmodel

import android.content.Context
import android.widget.Toast
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.User
import io.reactivex.subjects.BehaviorSubject

class TabFragmentViewModel(context: Context) : FragmentViewModel(context) {
    val user: BehaviorSubject<User> = BehaviorSubject.create()

    fun fetchUserInfo() {
        Twitter.getApiClient().accountService.verifyCredentials(true, false)
                .enqueue(object : Callback<User>() {
                    override fun failure(exception: TwitterException?) {
                        Toast.makeText(context, "failed get user info", Toast.LENGTH_SHORT).show()
                    }

                    override fun success(result: Result<User>?) {
                        result?.data?.run { user.onNext(this) }
                    }
                })
    }
}