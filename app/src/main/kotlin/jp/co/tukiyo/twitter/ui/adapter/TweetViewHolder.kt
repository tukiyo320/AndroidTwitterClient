package jp.co.tukiyo.twitter.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import jp.co.tukiyo.twitter.databinding.TweetItemBinding

class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: TweetItemBinding = DataBindingUtil.bind(itemView)
}