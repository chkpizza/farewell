package com.antique.story.data.model

import androidx.annotation.Keep

@Keep
data class PlaceInformationDto(
    val placeName: String? = null,
    val placeAddress: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)