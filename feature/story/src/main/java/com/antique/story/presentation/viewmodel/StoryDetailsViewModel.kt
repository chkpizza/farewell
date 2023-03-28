package com.antique.story.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.ApiState
import com.antique.common.util.SingleEvent
import com.antique.story.data.model.story.story.StoryUiState
import com.antique.story.domain.usecase.GetStoryDetailsUseCase
import com.antique.story.domain.usecase.RemoveStoryUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoryDetailsViewModel @Inject constructor(
    private val getStoryDetailsUseCase: GetStoryDetailsUseCase,
    private val removeStoryUseCase: RemoveStoryUseCase
): ViewModel() {
    private val _story = MutableLiveData<ApiState<StoryUiState>>()
    val story: LiveData<ApiState<StoryUiState>> get() = _story

    private val _removeStoryState = MutableLiveData<SingleEvent<Boolean>>()
    val removeStoryState: LiveData<SingleEvent<Boolean>> get() = _removeStoryState

    fun loadStory(storyId: String) {
        viewModelScope.launch {
            try {
                _story.value = ApiState.Loading
                val response = getStoryDetailsUseCase(storyId)
                _story.value = ApiState.Success(response)
            } catch (e: Exception) {
                _story.value = ApiState.Error(e)
            }
        }
    }

    fun removeStory(storyId: String) {
        viewModelScope.launch {
            try {
                val response = removeStoryUseCase(storyId)
                _removeStoryState.value = SingleEvent(response)
            } catch (_: Exception) {

            }
        }
    }
}