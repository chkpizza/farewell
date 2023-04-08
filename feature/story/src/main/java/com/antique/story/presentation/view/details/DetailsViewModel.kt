package com.antique.story.presentation.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.ApiState
import com.antique.story.domain.model.Story
import com.antique.story.domain.usecase.FetchStoryUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val fetchStoryUseCase: FetchStoryUseCase
) : ViewModel() {
    private val _story = MutableLiveData<ApiState<Story>>()
    val story: LiveData<ApiState<Story>> get() = _story

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
}