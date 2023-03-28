package com.antique.story.data.model.story.story

import androidx.annotation.Keep
import com.antique.story.data.model.place.Place

@Keep
data class StoryUiState(
    val body: String,
    val contents: List<Content>,
    val place: Place,
    val date: String,
    val storyId: String
) {
    constructor() : this("", emptyList(), Place(), "", "")
}
