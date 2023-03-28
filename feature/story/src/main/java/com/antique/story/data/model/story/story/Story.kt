package com.antique.story.data.model.story.story

import androidx.annotation.Keep

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
