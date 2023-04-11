package com.antique.story.presentation.binding

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.antique.common.util.ApiState
import com.antique.story.R
import com.antique.story.domain.model.Door
import com.antique.story.domain.model.Place
import com.antique.story.domain.model.PlaceInformation
import com.antique.story.domain.model.Story
import com.antique.story.presentation.view.add.SelectedPictureListAdapter
import com.antique.story.presentation.view.add.SelectedVideoListAdapter
import com.antique.story.presentation.view.details.MediaListAdapter
import com.antique.story.presentation.view.main.DoorAdapter
import com.antique.story.presentation.view.main.StoryListAdapter
import com.antique.story.presentation.view.picture.PictureListAdapter
import com.antique.story.presentation.view.place.PlaceListAdapter
import com.antique.story.presentation.view.video.VideoListAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("url")
    fun bindImage(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .load(it)
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("items")
    fun bindItems(recyclerView: RecyclerView, items: List<String>?) {
        items.let { _items ->
            when(recyclerView.adapter) {
                is SelectedPictureListAdapter -> {
                    (recyclerView.adapter as SelectedPictureListAdapter).submitList(_items)
                }
                is SelectedVideoListAdapter -> {
                    (recyclerView.adapter as SelectedVideoListAdapter).submitList(_items)
                }
                is VideoListAdapter -> {
                    (recyclerView.adapter as VideoListAdapter).submitList(_items)
                }
                is PictureListAdapter -> {
                    (recyclerView.adapter as PictureListAdapter).submitList(_items)
                }
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

    @JvmStatic
    @BindingAdapter("door")
    fun bindDoor(recyclerView: RecyclerView, item: ApiState<Door>?) {
        item?.let { state ->
            when (state) {
                is ApiState.Success -> {
                    (recyclerView.adapter as? ConcatAdapter)?.let {
                        val adapters = it.adapters
                        adapters.forEach { adapter ->
                            if (adapter is DoorAdapter) {
                                adapter.submitList(listOf(state.items))
                            }
                        }
                    }
                }
                is ApiState.Error -> {
                    Snackbar.make(
                        recyclerView,
                        recyclerView.context.getString(R.string.fetch_door_failure_text),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is ApiState.Loading -> {

                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("stories")
    fun bindStories(recyclerView: RecyclerView, item: ApiState<List<Story>>?) {
        item?.let { state ->
            when(state) {
                is ApiState.Success -> {
                    (recyclerView.adapter as? ConcatAdapter)?.let {
                        it.adapters.forEach { adapter ->
                            if(adapter is StoryListAdapter) {
                                adapter.submitList(state.items)
                            }
                        }
                    }
                }
                is ApiState.Error -> {
                    Snackbar.make(recyclerView, recyclerView.context.getString(R.string.fetch_story_failure_text), Snackbar.LENGTH_LONG).show()
                }
                is ApiState.Loading -> {

                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("story_date")
    fun bindStoryDate(textView: TextView, item: ApiState<Story>?) {
        item?.let { state ->
            when(state) {
                is ApiState.Success -> {
                    textView.text = state.items.date
                }
                is ApiState.Error -> {

                }
                is ApiState.Loading -> {

                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("story_body")
    fun bindStoryBody(textView: TextView, item: ApiState<Story>?) {
        item?.let { state ->
            when(state) {
                is ApiState.Success -> {
                    textView.text = state.items.body
                }
                is ApiState.Error -> {

                }
                is ApiState.Loading -> {

                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("story_place_visibility")
    fun bindStoryPlaceVisibility(constraintLayout: ConstraintLayout, item: ApiState<Story>?) {
        item?.let { state ->
            when(state) {
                is ApiState.Success -> {
                    constraintLayout.isVisible = state.items.place.placeName.isNotEmpty()
                }
                is ApiState.Error -> {

                }
                is ApiState.Loading -> {

                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("story_place_name")
    fun bindStoryPlaceName(textView: TextView, item: ApiState<Story>?) {
        item?.let { state ->
            when(state) {
                is ApiState.Success -> {
                    textView.text = state.items.place.placeName
                }
                is ApiState.Error -> {

                }
                is ApiState.Loading -> {

                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("story_media")
    fun bindStoryMedia(viewPager: ViewPager2, item: ApiState<Story>?) {
        item?.let { state ->
            when(state) {
                is ApiState.Success -> {
                    val media = mutableListOf<String>()

                    if(state.items.pictures.isNotEmpty() || state.items.videos.isNotEmpty()) {
                        viewPager.isVisible = true
                        if(state.items.pictures.isNotEmpty()) {
                            media.addAll(state.items.pictures)
                        }
                        if(state.items.videos.isNotEmpty()) {
                            media.addAll(state.items.videos)
                        }
                        (viewPager.adapter as? MediaListAdapter)?.submitList(media)
                    } else {
                        viewPager.isVisible = false
                    }
                }
                is ApiState.Error -> {

                }
                is ApiState.Loading -> {

                }
            }
        }
    }
}