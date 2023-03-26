package com.antique.story.data.story.story

import androidx.annotation.Keep
import com.antique.story.data.story.Place

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
