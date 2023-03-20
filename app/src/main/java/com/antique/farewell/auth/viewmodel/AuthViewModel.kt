package com.antique.farewell.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.SingleEvent
import com.antique.farewell.auth.usecase.IsExistUserUseCase
import com.antique.farewell.auth.usecase.IsWithdrawnUserUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val isExistUserUseCase: IsExistUserUseCase,
    private val isWithdrawnUserUseCase: IsWithdrawnUserUseCase
) : ViewModel() {
    private val _navigateToMain = MutableLiveData<SingleEvent<Boolean>>()
    val navigateToMain: LiveData<SingleEvent<Boolean>> get() = _navigateToMain

    private val _navigateToSetting = MutableLiveData<SingleEvent<Boolean>>()
    val navigateToSetting: LiveData<SingleEvent<Boolean>> get() = _navigateToSetting

    private val _navigateToWithdrawn = MutableLiveData<SingleEvent<Boolean>>()
    val navigateToWithdrawn: LiveData<SingleEvent<Boolean>> get() = _navigateToWithdrawn

    private val _timer = MutableLiveData<Long>()
    val timer: LiveData<Long> get() = _timer

    private lateinit var job: Job

    fun startTimer(seconds: Long) {
        if(::job.isInitialized) job.cancel()
        _timer.value = seconds

        job = viewModelScope.launch {
            while(timer.value!! > 0L) {
                _timer.value = timer.value?.minus(1)
                delay(1000)
            }
        }
    }

    fun register(uid: String) {
        viewModelScope.launch {
            try {
                if(!isExistUserUseCase(uid)) {
                    _navigateToSetting.value = SingleEvent(true)
                } else {
                    if(!isWithdrawnUserUseCase(uid)) {
                        _navigateToMain.value = SingleEvent(true)
                    } else {
                        _navigateToWithdrawn.value = SingleEvent(true)
                    }
                }
            } catch (_: Exception) {

            }
        }
    }
}