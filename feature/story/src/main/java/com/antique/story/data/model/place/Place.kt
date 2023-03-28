package com.antique.story.data.model.place

import androidx.annotation.Keep

@Keep
data class Place(
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double
) {
    constructor() : this("", "", 0.0, 0.0)
}
