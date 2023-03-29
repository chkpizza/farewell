package com.antique.story.domain.model

import androidx.annotation.Keep

@Keep
data class Video(
    val uri: String,
    val duration: Int
)