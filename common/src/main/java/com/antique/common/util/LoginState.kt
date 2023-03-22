package com.antique.common.util

import androidx.lifecycle.MutableLiveData

class LoginState {
    companion object {
        val isLogin = MutableLiveData<SingleEvent<Boolean>>()
    }
}