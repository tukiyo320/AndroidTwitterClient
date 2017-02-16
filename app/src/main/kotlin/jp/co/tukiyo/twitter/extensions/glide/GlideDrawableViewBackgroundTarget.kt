package jp.co.tukiyo.twitter.extensions.glide

import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation

class GlideDrawableViewBackgroundTarget(view: ImageView, val maxLoopCount: Int) : ViewBackgroundTarget<GlideDrawable>(view) {
    private var resource : GlideDrawable? = null

    constructor(view: ImageView) : this(view, GlideDrawable.LOOP_FOREVER)

    override fun setResource(resource: GlideDrawable) {
        setBackground(resource)
    }

    override fun onResourceReady(resource: GlideDrawable, glideAnimation: GlideAnimation<in GlideDrawable>?) {
        super.onResourceReady(resource, glideAnimation)
        this.resource = resource.apply {
            setLoopCount(maxLoopCount)
            start()
        }
    }

    override fun onStart() {
        resource?.start()
    }

    override fun onStop() {
        resource?.stop()
    }
}