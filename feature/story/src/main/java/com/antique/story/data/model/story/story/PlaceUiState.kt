package com.antique.story.data.model.story.story

import androidx.annotation.Keep

@Keep
data class PlaceUiState(
    val places: List<Place>,
    val isEnd: Boolean
) {
    constructor() : this(listOf(), true)
}

@Keep
data class Place(
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double
) {
    constructor() : this("", "", 0.0, 0.0)
}
