package jp.co.tukiyo.twitter.extensions.glide

import android.graphics.drawable.Drawable
import android.view.View

class DrawableViewBackgroundTarget(view: View): ViewBackgroundTarget<Drawable>(view) {
    override fun setResource(resource: Drawable) {
        setBackground(resource)
    }
}