package com.antique.story.data.story.story

import androidx.annotation.Keep

@Keep
data class Content(
    val uri: String,
    val type: String
) {
    constructor() : this("", "")
}