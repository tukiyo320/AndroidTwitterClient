package jp.co.tukiyo.twitter.ui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.twitter.sdk.android.core.models.MediaEntity
import jp.co.tukiyo.twitter.R
import jp.co.tukiyo.twitter.databinding.MediaItemBinding

class MediaAdapter(val context: Context) : BaseAdapter() {
    val itemId: Int = R.layout.media_item
    val items : MutableList<MediaEntity> = mutableListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding : MediaItemBinding
        if(convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), itemId, parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as MediaItemBinding
        }

        return binding.apply {
            getItem(position)?.let {
                media = it
                Glide.with(context)
                        .load(it.mediaUrl)
                        .centerCrop()
                        .fitCenter()
                        .into(binding.mediaItemImage)
            }
        }.root
    }

    override fun getItem(position: Int): MediaEntity? {
        return items.getOrNull(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    fun add(position: Int, media: MediaEntity) {
        items.add(position, media)
        notifyDataSetChanged()
    }
}