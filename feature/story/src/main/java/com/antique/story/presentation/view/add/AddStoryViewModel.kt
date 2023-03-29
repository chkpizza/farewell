package com.antique.story.presentation.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AddStoryViewModel @Inject constructor() : ViewModel() {
    private val _selectedPictures = MutableLiveData<List<String>>(emptyList())
    val selectedPictures: LiveData<List<String>> get() = _selectedPictures

    private val _selectedVideos = MutableLiveData<List<String>>(emptyList())
    val selectedVideos: LiveData<List<String>> get() = _selectedVideos

    fun setPictures(selectedPictures: List<String>) {
        _selectedPictures.value = selectedPictures
    }

    fun removePicture(picture: String) {
        _selectedPictures.value?.let {
            _selectedPictures.value = it.filter { item -> item != picture }
        }
    }

    fun setVideos(selectedVideos: List<String>) {
        _selectedVideos.value = selectedVideos
    }

    fun removeVideo(video: String) {
        _selectedVideos.value?.let {
            _selectedVideos.value = it.filter { item -> item != video }
        }
    }
}