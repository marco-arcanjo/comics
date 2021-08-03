package com.example.comics.comic

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.comics.R

@BindingAdapter("image")
fun loadImage(imageView: ImageView, path: String?) {
    path?.let {
        Glide.with(imageView.context)
            .load(path.toUri().buildUpon().scheme("https").build())
            .placeholder(R.drawable.image_not_available_portrait_medium)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}