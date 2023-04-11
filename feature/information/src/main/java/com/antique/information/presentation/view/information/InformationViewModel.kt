package com.antique.information.presentation.view.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.ApiState
import com.antique.information.domain.model.Preview
import com.antique.information.domain.usecase.FetchPreviewUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class InformationViewModel @Inject constructor(
    private val fetchPreviewUseCase: FetchPreviewUseCase
) : ViewModel() {
    private val _preview = MutableLiveData<ApiState<List<Preview>>>()
    val preview: LiveData<ApiState<List<Preview>>> get() = _preview

    fun fetchPreview() {
        viewModelScope.launch {
            try {
                val response = fetchPreviewUseCase()
                _preview.value = ApiState.Success(response)
            } catch (e: Exception) {
                _preview.value = ApiState.Error(e)
            }
        }
    }
}