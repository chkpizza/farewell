package com.antique.story.presentation.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.ApiState
import com.antique.story.domain.model.Door
import com.antique.story.domain.model.Story
import com.antique.story.domain.usecase.FetchDoorUseCase
import com.antique.story.domain.usecase.FetchStoriesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoryViewModel @Inject constructor(
    private val fetchDoorUseCase: FetchDoorUseCase,
    private val fetchStoriesUseCase: FetchStoriesUseCase
) : ViewModel() {
    private val _door = MutableLiveData<ApiState<Door>>()
    val door: LiveData<ApiState<Door>> get() = _door

    private val _stories = MutableLiveData<ApiState<List<Story>>>()
    val stories: LiveData<ApiState<List<Story>>> get() = _stories

    private lateinit var index: String
    private var isLoading = false

    fun fetchStories() {
        viewModelScope.launch {
            try {
                if(stories.value == null) {
                    isLoading = true
                    val response = fetchStoriesUseCase()
                    if(response.isNotEmpty()) {
                        index = response.last().id
                    }
                    _stories.value = ApiState.Success(response)
                }
            } catch (e: Exception) {
                _stories.value = ApiState.Error(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchMoreStories() {
        viewModelScope.launch {
            try {
                if(::index.isInitialized && !isLoading) {
                    isLoading = true
                    val response = fetchStoriesUseCase(index)
                    if(response.isNotEmpty()) {
                        index = response.last().id
                        stories.value?.let {
                            (it as? ApiState.Success)?.let { state ->
                                _stories.value = ApiState.Success(
                                    state.items.toMutableList().apply { addAll(response) }.toList()
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _stories.value = ApiState.Error(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchDoor() {
        viewModelScope.launch {
            try {
                val response = fetchDoorUseCase()
                _door.value = ApiState.Success(response)
            } catch (e: Exception) {
                _door.value = ApiState.Error(e)
            }
        }
    }

    fun addNewStory(story: Story) {
        stories.value?.let {
            (it as? ApiState.Success)?.let { state ->
                val newList = state.items.toMutableList()
                newList.add(0, story)
                _stories.value = ApiState.Success(newList.toList())
            }
        }
    }

    fun excludeStory(id: String) {
        stories.value?.let {
            (it as? ApiState.Success)?.let { state ->
                _stories.value = ApiState.Success(state.items.filter { story -> story.id != id })
            }
        }
    }
}