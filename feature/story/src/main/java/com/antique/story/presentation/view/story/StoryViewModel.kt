package com.antique.story.presentation.view.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.ApiState
import com.antique.story.domain.model.Door
import com.antique.story.domain.model.Story
import com.antique.story.domain.usecase.GetDoorUseCase
import com.antique.story.domain.usecase.GetMoreStoriesUseCase
import com.antique.story.domain.usecase.GetStoriesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoryViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getMoreStoriesUseCase: GetMoreStoriesUseCase,
    private val getDoorUseCase: GetDoorUseCase
) : ViewModel() {
    private val _story = MutableLiveData<ApiState<List<Story>>>()
    val story: LiveData<ApiState<List<Story>>> get() = _story

    private val _door = MutableLiveData<ApiState<Door>>()
    val door: LiveData<ApiState<Door>> get() = _door

    private lateinit var idx: String
    private var isLoading: Boolean = false

    fun loadDoor() {
        viewModelScope.launch {
            try {
                val response = getDoorUseCase()
                _door.value = ApiState.Success(response)
            } catch (e: Exception) {
                _door.value = ApiState.Error(e)
            }
        }
    }

    fun loadStories() {
        viewModelScope.launch {
            try {
                if(story.value == null) {
                    val response = getStoriesUseCase()
                    if (response.isNotEmpty()) {
                        idx = response.last().storyId
                    }
                    _story.value = ApiState.Success(response)
                }
            } catch (e: Exception) {
                _story.value = ApiState.Error(e)
            }
        }
    }

    fun loadMoreStories() {
        viewModelScope.launch {
            try {
                if(::idx.isInitialized && !isLoading) {
                    val response = getMoreStoriesUseCase(idx)

                    if(response.isNotEmpty()) {
                        idx = response.last().storyId
                        story.value?.let {
                            (it as? ApiState.Success)?.let { state ->
                                _story.value = ApiState.Success(
                                    state.items.toMutableList().apply {
                                        addAll(response)
                                    }.toList()
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _story.value = ApiState.Error(e)
            }
        }
    }

    fun updateStory(story: Story) {
        this.story.value?.let {
            (it as? ApiState.Success)?.let { state ->
                val newList = state.items.toMutableList()
                newList.add(0, story)
                _story.value = ApiState.Success(newList.toList())
            }
        }
    }

    fun removeStory(storyId: String) {
        story.value?.let {
            (it as? ApiState.Success)?.let { state ->
                val newList = state.items.filter { story -> story.storyId != storyId }
                _story.value = ApiState.Success(newList)
            }
        }
    }
}