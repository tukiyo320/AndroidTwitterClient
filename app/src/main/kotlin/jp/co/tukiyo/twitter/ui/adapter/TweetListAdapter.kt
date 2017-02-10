package jp.co.tukiyo.twitter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.chuross.recyclerviewadapters.ItemAdapter
import com.twitter.sdk.android.core.models.Tweet
import jp.co.tukiyo.twitter.R

class TweetListAdapter(context: Context) : ItemAdapter<Tweet, TweetViewHolder>(context) {
    val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getAdapterId(): Int = R.layout.tweet_item

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TweetViewHolder {
        return TweetViewHolder(inflater.inflate(R.layout.tweet_item, parent, false))
    }

    override fun onBindViewHolder(holder: TweetViewHolder?, position: Int) {
        holder?.textView?.text = get(position).text
    }
}
