package com.antique.story.data.place

import androidx.annotation.Keep

@Keep
data class PlaceResponse(
    val documents: List<Document>,
    val meta: Meta
)