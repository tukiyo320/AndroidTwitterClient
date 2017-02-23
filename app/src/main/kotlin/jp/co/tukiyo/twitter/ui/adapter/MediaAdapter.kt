package jp.co.tukiyo.twitter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.twitter.sdk.android.core.models.MediaEntity
import jp.co.tukiyo.twitter.R

class MediaAdapter(val context: Context) : BaseAdapter() {
    val itemId: Int = R.layout.media_item
    val items : MutableList<MediaEntity> = mutableListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(itemId, parent, false)

        val holder = MediaViewHolder(view).apply {
            binding.media = getItem(position)
        }
        return holder.binding.root
    }

    override fun getItem(position: Int): MediaEntity {
        return items[position]
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