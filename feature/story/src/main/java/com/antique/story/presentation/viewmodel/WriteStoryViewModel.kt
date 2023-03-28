package com.antique.story.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.ApiState
import com.antique.common.util.Constant
import com.antique.story.data.model.place.Place
import com.antique.story.data.model.story.story.Video
import com.antique.story.data.model.story.story.Content
import com.antique.story.data.model.place.PlaceUiState
import com.antique.story.data.model.story.story.StoryUiState
import com.antique.story.domain.usecase.GetLocationsUseCase
import com.antique.story.domain.usecase.RegisterStoryUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WriteStoryViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val registerStoryUseCase: RegisterStoryUseCase
) : ViewModel() {
    private val _photos = MutableLiveData<List<String>>()
    val photos: LiveData<List<String>> get() = _photos

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> get() = _videos

    private val _place = MutableLiveData<Place?>()
    val place: LiveData<Place?> get() = _place

    private val _places = MutableLiveData<ApiState<PlaceUiState>?>()
    val places: LiveData<ApiState<PlaceUiState>?> get() = _places

    private val _registerStoryState = MutableLiveData<ApiState<StoryUiState>>()
    val registerStoryState: LiveData<ApiState<StoryUiState>> get() = _registerStoryState

    private lateinit var _key: String
    private lateinit var _query: String
    private var _page: Int = 1

    fun bindPhotos(photos: List<String>) {
        _photos.value = photos
    }

    fun bindVideos(videos: List<Video>) {
        _videos.value = videos
    }

    fun bindPlace(place: Place) {
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

    fun registerStory(body: String) {
        viewModelScope.launch {
            try {
                val contents = mutableListOf<Content>()
                photos.value?.let {
                    if(it.isNotEmpty()) {
                        it.forEach { photo ->
                            contents.add(Content(photo, Constant.PHOTO_CONTENT))
                        }
                    }
                }

                videos.value?.let {
                    if(it.isNotEmpty()) {
                        it.forEach { video ->
                            contents.add(Content(video.uri, Constant.VIDEO_CONTENT))
                        }
                    }
                }

                val place = place.value ?: Place()
                val date = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time)

                _registerStoryState.value = ApiState.Loading
                val response = registerStoryUseCase(body, contents, place, date)
                _registerStoryState.value = ApiState.Success(response)
            } catch (e: Exception) {
                _registerStoryState.value = ApiState.Error(e)
            }
        }
    }

    fun clear() {
        _places.value = null
    }
}