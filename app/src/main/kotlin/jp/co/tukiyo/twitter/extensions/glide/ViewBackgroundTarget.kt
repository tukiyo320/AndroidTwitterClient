package jp.co.tukiyo.twitter.extensions.glide

import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.ViewTarget

abstract class ViewBackgroundTarget<T>(view: View) : ViewTarget<View, T>(view), GlideAnimation.ViewAdapter {
    override fun onLoadCleared(placeholder: Drawable?) {
        setBackground(placeholder)
    }

    override fun onLoadStarted(placeholder: Drawable?) {
        setBackground(placeholder)
    }

    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
        setBackground(errorDrawable)
    }

    override fun onResourceReady(resource: T, glideAnimation: GlideAnimation<in T>?) {
        if(glideAnimation == null || !glideAnimation.animate(resource, this)) {
            setResource(resource)
        }
    }

    override fun getCurrentDrawable(): Drawable {
        return view.background
    }

    override fun setDrawable(drawable: Drawable?) {
        setBackground(drawable)
    }

    fun setBackground(drawable: Drawable?) {
        view.background = drawable
    }

    abstract fun setResource(resource: T)
}