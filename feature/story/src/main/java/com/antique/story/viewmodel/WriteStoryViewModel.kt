package com.antique.story.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class WriteStoryViewModel @Inject constructor() : ViewModel() {
    private val _photos = MutableLiveData<List<String>>()
    val photos: LiveData<List<String>> get() = _photos

    private val _videos = MutableLiveData<List<String>>()
    val videos: LiveData<List<String>> get() = _videos

    fun bindPhotos(photos: List<String>) {
        _photos.value = photos
    }

    fun bindVideos(videos: List<String>) {
        _videos.value = videos
    }
}