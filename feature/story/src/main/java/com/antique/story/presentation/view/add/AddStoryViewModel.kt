package com.antique.story.presentation.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.story.domain.usecase.RegisterStoryUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddStoryViewModel @Inject constructor(
    private val registerStoryUseCase: RegisterStoryUseCase
) : ViewModel() {
    private val _selectedPictures = MutableLiveData<List<String>>(emptyList())
    val selectedPictures: LiveData<List<String>> get() = _selectedPictures

    private val _selectedVideos = MutableLiveData<List<String>>(emptyList())
    val selectedVideos: LiveData<List<String>> get() = _selectedVideos

    private val _register = MutableLiveData<Boolean>()
    val register: LiveData<Boolean> get() = _register

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

    fun registerStory(body: String) {
        viewModelScope.launch {
            try {
                val pictures = _selectedPictures.value?.let {
                    it.ifEmpty {
                        emptyList()
                    }
                } ?: emptyList()

                val videos = _selectedVideos.value?.let {
                    it.ifEmpty {
                        emptyList()
                    }
                } ?: emptyList()

                val date = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time)

                val response = registerStoryUseCase(body, pictures, videos, date)
                _register.value = response
            } catch (e: Exception) {
                _register.value = false
            }
        }
    }
}