package com.antique.story.domain.model

import androidx.annotation.Keep

@Keep
data class Content(
    val uri: String,
    val type: String
) {
    constructor() : this("", "")
}