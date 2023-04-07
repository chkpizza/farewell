package com.antique.story.data.model

data class StoryDto(
    val body: String? = null,
    val pictures: List<String>? = null,
    val videos: List<String>? = null,
    val place: PlaceInformationDto? = null,
    val date: String? = null,
    val id: String? = null
)
