package com.antique.story.data.model.place

import androidx.annotation.Keep

@Keep
data class PlaceUiState(
    val places: List<Place>,
    val isEnd: Boolean
) {
    constructor() : this(listOf(), true)
}


