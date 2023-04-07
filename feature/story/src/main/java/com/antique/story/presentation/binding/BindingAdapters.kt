package com.antique.story.presentation.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.common.util.ApiState
import com.antique.story.R
import com.antique.story.domain.model.Place
import com.antique.story.domain.model.PlaceInformation
import com.antique.story.presentation.view.add.SelectedPictureListAdapter
import com.antique.story.presentation.view.add.SelectedVideoListAdapter
import com.antique.story.presentation.view.picture.PictureListAdapter
import com.antique.story.presentation.view.place.PlaceListAdapter
import com.antique.story.presentation.view.video.VideoListAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

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

    @JvmStatic
    @BindingAdapter("places")
    fun bindPlaces(recyclerView: RecyclerView, item: ApiState<Place>) {
        when(item) {
            is ApiState.Success -> {
                (recyclerView.adapter as? PlaceListAdapter)?.submitList(item.items.places)
            }
            is ApiState.Error -> {
                Snackbar.make(recyclerView, recyclerView.context.getString(R.string.place_search_failure_text), Snackbar.LENGTH_SHORT).show()
            }
            is ApiState.Loading -> {

            }
        }
    }

    @JvmStatic
    @BindingAdapter("place_name")
    fun bindPlaceName(textView: TextView, placeInformation: PlaceInformation?) {
        placeInformation?.let {
            textView.text = it.placeName
        } ?: run {
            textView.text = ""
        }
    }

    @JvmStatic
    @BindingAdapter("place_visibility")
    fun bindPlaceVisibility(constraintLayout: ConstraintLayout, placeInformation: PlaceInformation?) {
        placeInformation?.let {
            constraintLayout.visibility = View.VISIBLE
        } ?: run {
            constraintLayout.visibility = View.GONE
        }
    }
}