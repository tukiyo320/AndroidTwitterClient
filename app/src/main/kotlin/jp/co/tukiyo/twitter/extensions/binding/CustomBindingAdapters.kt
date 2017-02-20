package jp.co.tukiyo.twitter.extensions.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.loadImage(imageUrl: String) {
    Glide.with(context)
            .load(imageUrl)
            .into(this)
}
