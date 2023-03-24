package com.antique.story.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antique.story.data.Video
import javax.inject.Inject

class WriteStoryViewModel @Inject constructor() : ViewModel() {
    private val _photos = MutableLiveData<List<String>>()
    val photos: LiveData<List<String>> get() = _photos

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> get() = _videos

    fun bindPhotos(photos: List<String>) {
        _photos.value = photos
    }

    fun bindVideos(videos: List<Video>) {
        _videos.value = videos
    }

    fun removePhoto(photo: String) {
        _photos.value?.let {
            _photos.value = it.toMutableList().apply {
                remove(photo)
            }.toList()
        }
    }

    fun removeVideo(video: Video) {
        _videos.value?.let {
            _videos.value = it.toMutableList().apply {
                remove(video)
            }.toList()
        }
    }
}