package jp.co.tukiyo.twitter.extensions.glide

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View

class BitmapViewBackgroundTarget(view: View) : ViewBackgroundTarget<Bitmap>(view){
    override fun setResource(resource: Bitmap) {
        setBackground(BitmapDrawable(view.resources, resource))
    }
}