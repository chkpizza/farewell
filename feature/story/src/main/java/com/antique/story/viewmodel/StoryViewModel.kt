package com.antique.story.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antique.common.util.ApiState
import com.antique.story.data.story.StoryUiState
import javax.inject.Inject

class StoryViewModel @Inject constructor() : ViewModel() {
    private val _story = MutableLiveData<ApiState<List<StoryUiState>>>()
    val story: LiveData<ApiState<List<StoryUiState>>> get() = _story

    fun updateStory(storyUiState: StoryUiState) {
        story.value?.let {
            (it as? ApiState.Success)?.let { state ->
                val newList = mutableListOf<StoryUiState>()
                _story.value = ApiState.Success(state.items.toMutableList().also { story ->
                    newList.add(storyUiState)
                    newList.addAll(story)
                }.toList())
            }
        }
    }
}