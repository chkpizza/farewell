package com.antique.settings.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.ApiState
import com.antique.common.util.SingleEvent
import com.antique.settings.domain.usecase.ChangeNickNameUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val changeNickNameUseCase: ChangeNickNameUseCase
) : ViewModel() {
    private val _changeNickNameState = MutableLiveData<ApiState<Boolean>>()
    val changeNickNameState: LiveData<ApiState<Boolean>> get() = _changeNickNameState

    fun changeNickName(nickName: String) {
        viewModelScope.launch {
            try {
                val response = changeNickNameUseCase(nickName)
                _changeNickNameState.value = ApiState.Success(response)
            } catch (e: Exception) {
                _changeNickNameState.value = ApiState.Error(e)
            }
        }
    }
}