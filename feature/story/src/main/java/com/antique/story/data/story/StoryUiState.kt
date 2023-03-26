package com.antique.story.data.story

import androidx.annotation.Keep

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
