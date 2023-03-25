package com.antique.story.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.ApiState
import com.antique.common.util.SingleEvent
import com.antique.story.data.place.PlaceInformation
import com.antique.story.data.place.PlaceUiState
import com.antique.story.data.place.Video
import com.antique.story.usecase.GetLocationsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class WriteStoryViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {
    private val _photos = MutableLiveData<List<String>>()
    val photos: LiveData<List<String>> get() = _photos

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> get() = _videos

    private val _place = MutableLiveData<PlaceInformation?>()
    val place: LiveData<PlaceInformation?> get() = _place

    private val _places = MutableLiveData<ApiState<PlaceUiState>?>()
    val places: LiveData<ApiState<PlaceUiState>?> get() = _places

    private lateinit var _key: String
    private lateinit var _query: String
    private var _page: Int = 1

    fun bindPhotos(photos: List<String>) {
        _photos.value = photos
    }

    fun bindVideos(videos: List<Video>) {
        _videos.value = videos
    }

    fun bindPlace(place: PlaceInformation) {
        _place.value = place
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

    fun removeLocation() {
        _place.value = null
    }

    fun getLocations(key: String, query: String) {
        viewModelScope.launch {
            try {
                _key = key
                _query = query
                _page = 1
                val response = getLocationsUseCase(key, query, _page)
                _places.value = ApiState.Success(response)
            } catch (e: Exception) {
                _places.value = ApiState.Error(e)
            }
        }
    }

    fun getMoreLocations() {
        viewModelScope.launch {
            try {
                _page++
                places.value?.let {
                    (it as? ApiState.Success)?.let { state ->
                        if(!state.items.isEnd) {
                            val response = getLocationsUseCase(_key, _query, _page)

                            val newList = state.items.places.toMutableList()
                            newList.addAll(response.places)
                            _places.value = ApiState.Success(PlaceUiState(newList, response.isEnd))
                        }
                    }
                }
            } catch (e: Exception) {
                _places.value = ApiState.Error(e)
            }
        }
    }

    fun clear() {
        _places.value = null
    }
}