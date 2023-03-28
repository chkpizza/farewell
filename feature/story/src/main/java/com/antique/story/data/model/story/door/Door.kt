package com.antique.story.data.model.story.door

import androidx.annotation.Keep

@Keep
data class Door(
    val nickName: String,
    val doorText: String
) {
    constructor() : this("", "")
}