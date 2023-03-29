package com.antique.story.domain.model

import androidx.annotation.Keep
import com.antique.story.data.model.Place

@Keep
data class Story(
    val body: String,
    val contents: List<Content>,
    val place: Place,
    val date: String,
    val storyId: String
) {
    constructor() : this("", emptyList(), Place(), "", "")
}
