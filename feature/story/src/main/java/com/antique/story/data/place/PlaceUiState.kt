package com.antique.story.data.place

import androidx.annotation.Keep

@Keep
data class PlaceUiState(
    val places: List<PlaceInformation>,
    val isEnd: Boolean
)

@Keep
data class PlaceInformation(
    val placeName: String,
    val placeAddress: String,
    val latitude: Double,
    val longitude: Double
)
