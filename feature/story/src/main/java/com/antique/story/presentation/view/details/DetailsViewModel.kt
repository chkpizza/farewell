package com.antique.story.presentation.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.ApiState
import com.antique.story.domain.model.Story
import com.antique.story.domain.usecase.FetchStoryUseCase
import com.antique.story.domain.usecase.RemoveStoryUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val fetchStoryUseCase: FetchStoryUseCase,
    private val removeStoryUseCase: RemoveStoryUseCase
) : ViewModel() {
    private val _story = MutableLiveData<ApiState<Story>>()
    val story: LiveData<ApiState<Story>> get() = _story

    private val _removeStory = MutableLiveData<ApiState<Boolean>>()
    val removeStory: LiveData<ApiState<Boolean>> get() = _removeStory

    fun fetchStory(id: String) {
        viewModelScope.launch {
            try {
                val response = fetchStoryUseCase(id)
                _story.value = ApiState.Success(response)
            } catch (e: Exception) {
                _story.value = ApiState.Error(e)
            }
        }
    }

    fun removeStory(id: String) {
        viewModelScope.launch {
            try {
                when(removeStoryUseCase(id)) {
                    true -> {
                        _removeStory.value = ApiState.Success(true)
                    }
                    false -> {
                        _removeStory.value = ApiState.Success(false)
                    }
                }
            } catch (e: Exception) {
                _removeStory.value = ApiState.Error(e)
            }

        }
    }
}