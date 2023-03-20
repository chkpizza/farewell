package com.antique.farewell.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
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
}