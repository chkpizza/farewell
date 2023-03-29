package com.antique.story.presentation.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.story.presentation.view.add.SelectedPictureListAdapter
import com.antique.story.presentation.view.add.SelectedVideoListAdapter
import com.antique.story.presentation.view.picture.PictureListAdapter
import com.antique.story.presentation.view.video.VideoListAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("url")
    fun bindImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("items")
    fun bindItems(recyclerView: RecyclerView, items: List<String>) {
        when(recyclerView.adapter) {
            is SelectedPictureListAdapter -> {
                (recyclerView.adapter as SelectedPictureListAdapter).submitList(items)
            }
            is SelectedVideoListAdapter -> {
                (recyclerView.adapter as SelectedVideoListAdapter).submitList(items)
            }
            is VideoListAdapter -> {
                (recyclerView.adapter as VideoListAdapter).submitList(items)
            }
            is PictureListAdapter -> {
                (recyclerView.adapter as PictureListAdapter).submitList(items)
            }
        }
    }
}