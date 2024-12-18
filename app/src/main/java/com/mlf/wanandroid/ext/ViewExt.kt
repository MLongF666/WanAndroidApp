package com.mlf.wanandroid.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mlf.wanandroid.R

fun ImageView.load(url: String, showPlaceholder: Boolean = true) {
    if (showPlaceholder) {
        Glide.with(context).load(url)
            .placeholder(R.drawable.ic_default_img)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    } else {
        Glide.with(context).load(url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    }
}