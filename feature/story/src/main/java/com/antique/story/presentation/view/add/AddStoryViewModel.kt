package com.antique.story.presentation.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AddStoryViewModel @Inject constructor() : ViewModel() {
    private val _selectedPictures = MutableLiveData<List<String>>(emptyList())
    val selectedPictures: LiveData<List<String>> get() = _selectedPictures

    fun setPictures(selectedPictures: List<String>) {
        _selectedPictures.value = selectedPictures
    }

    fun removePicture(picture: String) {
        _selectedPictures.value?.let {
            _selectedPictures.value = it.filter { item -> item != picture }
        }
    }
}