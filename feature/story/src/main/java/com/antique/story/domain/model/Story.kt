package com.antique.story.domain.model

import androidx.annotation.Keep

@Keep
data class Story(
    val body: String,
    val pictures: List<String>,
    val videos: List<String>,
    val place: PlaceInformation,
    val date: String,
    val id: String
)
