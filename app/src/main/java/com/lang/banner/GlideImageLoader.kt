package com.lang.banner

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created by lang on 2018/3/14.
 */
class GlideImageLoader : ImageLoader() {

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        context?.let { imageView?.let { it1 -> Glide.with(it).load(path).into(it1) } }
    }
}