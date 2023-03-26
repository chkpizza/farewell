package com.antique.story.data.story.door

import androidx.annotation.Keep

@Keep
data class DoorUiState(
    val nickName: String,
    val doorText: String
) {
    constructor() : this("", "")
}