package jp.co.tukiyo.twitter.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import jp.co.tukiyo.twitter.R

class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.tweet_item_text) as TextView
}