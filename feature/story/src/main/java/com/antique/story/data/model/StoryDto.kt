package com.antique.story.data.model

import androidx.annotation.Keep
import com.antique.story.domain.model.Content

@Keep
data class StoryDto(
    val body: String,
    val contents: List<Content>,
    val place: Place,
    val date: String,
    val storyId: String
) {
    constructor() : this("", emptyList(), Place(), "", "")
}
