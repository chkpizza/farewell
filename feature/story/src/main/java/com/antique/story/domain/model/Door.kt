package com.antique.story.domain.model

import androidx.annotation.Keep

@Keep
data class Door(
    val nickName: String,
    val doorText: String
) {
    constructor() : this("", "")
}