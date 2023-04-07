package com.antique.story.domain.model

import androidx.annotation.Keep

@Keep
data class PlaceInformation(
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double
) {
    constructor() : this("", "", 0.0, 0.0)
}
