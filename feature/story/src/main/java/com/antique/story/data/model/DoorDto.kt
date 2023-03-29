package com.antique.story.data.model

import androidx.annotation.Keep

@Keep
data class DoorDto(
    val nickName: String,
    val doorText: String
) {
    constructor() : this("", "")
}