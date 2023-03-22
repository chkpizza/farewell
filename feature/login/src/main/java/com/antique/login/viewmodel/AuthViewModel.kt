package com.antique.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.SingleEvent
import com.antique.common.data.UserUiState
import com.antique.login.usecase.IsExistUserUseCase
import com.antique.login.usecase.IsWithdrawnUserUseCase
import com.antique.login.usecase.RegisterUserUseCase
import com.antique.login.usecase.WithdrawnCancelUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val isExistUserUseCase: IsExistUserUseCase,
    private val isWithdrawnUserUseCase: IsWithdrawnUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val withdrawnUserUseCase: WithdrawnCancelUseCase
) : ViewModel() {
    private val _navigateToMain = MutableLiveData<SingleEvent<Boolean>>()
    val navigateToMain: LiveData<SingleEvent<Boolean>> get() = _navigateToMain

    private val _navigateToSettings = MutableLiveData<SingleEvent<Boolean>>()
    val navigateToSettings: LiveData<SingleEvent<Boolean>> get() = _navigateToSettings

    private val _navigateToWithdrawn = MutableLiveData<SingleEvent<Boolean>>()
    val navigateToWithdrawn: LiveData<SingleEvent<Boolean>> get() = _navigateToWithdrawn

    private val _registerState = MutableLiveData<SingleEvent<Boolean>>()
    val registerState: LiveData<SingleEvent<Boolean>> get() = _registerState

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

    fun check(uid: String) {
        viewModelScope.launch {
            try {
                if(!isExistUserUseCase(uid)) {
                    _navigateToSettings.value = SingleEvent(true)
                } else {
                    if(!isWithdrawnUserUseCase(uid)) {
                        _navigateToMain.value = SingleEvent(true)
                    } else {
                        _navigateToWithdrawn.value = SingleEvent(true)
                    }
                }
            } catch (_: Exception) { }
        }
    }

    fun isRegisteredUser(uid: String) {
        viewModelScope.launch {
            try {
                val response = isExistUserUseCase(uid)
                _registerState.value = SingleEvent(response)
            } catch (_: Exception) { }
        }
    }

    fun registerUser(uid: String, nickName: String) {
        viewModelScope.launch {
            try {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)
                val response = registerUserUseCase(UserUiState(uid, nickName, "", date))
                _navigateToMain.value = SingleEvent(response)
            } catch (_: Exception) { }
        }
    }

    fun withdrawnCancel(uid: String) {
        viewModelScope.launch {
            try {
                val response = withdrawnUserUseCase(uid)
                _navigateToMain.value = SingleEvent(response)
            } catch (_: Exception) {}
        }
    }
}