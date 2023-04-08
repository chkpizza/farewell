package com.antique.story.presentation.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.ApiState
import com.antique.story.domain.model.PlaceInformation
import com.antique.story.domain.model.Story
import com.antique.story.domain.usecase.RegisterStoryUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddStoryViewModel @Inject constructor(
    private val registerStoryUseCase: RegisterStoryUseCase
) : ViewModel() {
    private val _selectedPictures = MutableLiveData<List<String>>()
    val selectedPictures: LiveData<List<String>> get() = _selectedPictures

    private val _selectedVideos = MutableLiveData<List<String>>()
    val selectedVideos: LiveData<List<String>> get() = _selectedVideos

    private val _selectedPlace = MutableLiveData<PlaceInformation?>()
    val selectedPlace: LiveData<PlaceInformation?> get() = _selectedPlace

    private val _register = MutableLiveData<ApiState<Story>>()
    val register: LiveData<ApiState<Story>> get() = _register

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

    fun setPlace(selectedPlace: PlaceInformation) {
        _selectedPlace.value = selectedPlace
    }

    fun removePlace() {
        _selectedPlace.value = null
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

                val place = _selectedPlace.value ?: PlaceInformation()

                val date = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time)

                _register.value = ApiState.Loading
                val response = registerStoryUseCase(body, pictures, videos, place, date)
                _register.value = ApiState.Success(response)

            } catch (e: Exception) {
                _register.value = ApiState.Error(e)
            }
        }
    }
}