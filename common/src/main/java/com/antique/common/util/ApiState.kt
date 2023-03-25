package com.antique.common.util

import androidx.annotation.Keep

@Keep
sealed class ApiState<out T> {
    object Loading : ApiState<Nothing>()
    data class Success<T>(val items: T) : ApiState<T>()
    data class Error(val exception: Exception) : ApiState<Nothing>()
}