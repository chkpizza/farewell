package com.antique.story.domain.model

import androidx.annotation.Keep
import com.antique.story.data.model.Place

@Keep
data class Place(
    val places: List<Place>,
    val isEnd: Boolean
) {
    constructor() : this(listOf(), true)
}


