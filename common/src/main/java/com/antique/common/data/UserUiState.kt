package com.antique.common.data

import androidx.annotation.Keep

@Keep
data class UserUiState(
    val uid: String,
    val nickName: String,
    val profileImageUrl: String,
    val date: String
) {
    constructor() : this("", "", "", "")
}