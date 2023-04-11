package com.antique.story.domain.model

import androidx.annotation.Keep

@Keep
data class Place(
    val places: List<PlaceInformation>,
    val isEnd: Boolean
)
