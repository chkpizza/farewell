package com.antique.story.data.model

import androidx.annotation.Keep

@Keep
data class PlaceInformationDto(
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double
) {
    constructor() : this("", "", 0.0, 0.0)
}
