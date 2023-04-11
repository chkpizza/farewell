package com.antique.story.data.mapper

import com.antique.story.data.model.PlaceDto
import com.antique.story.data.model.PlaceInformationDto
import com.antique.story.data.model.StoryDto
import com.antique.story.domain.model.Place
import com.antique.story.domain.model.PlaceInformation
import com.antique.story.domain.model.Story

fun mapperToDomain(placeDto: PlaceDto): Place {
    val placeInformation = mutableListOf<PlaceInformation>()
    placeDto.documents.forEach {
        placeInformation.add(
            PlaceInformation(
                it.place_name,
                it.address_name,
                it.y.toDouble(),
                it.x.toDouble()
            )
        )
    }
    return Place(placeInformation, placeDto.meta.is_end)
}

fun mapperToDomain(storyDto: StoryDto): Story {
    return Story(
        storyDto.body ?: "",
        storyDto.pictures ?: emptyList(),
        storyDto.videos ?: emptyList(),
        PlaceInformation(
            storyDto.place?.placeName ?: "",
            storyDto.place?.placeAddress ?: "",
            storyDto.place?.latitude ?: 0.0,
            storyDto.place?.longitude ?: 0.0
        ),
        storyDto.date ?: "",
        storyDto.id ?: ""
    )
}

fun mapperToDto(place: PlaceInformation): PlaceInformationDto {
    return PlaceInformationDto(place.placeName, place.placeAddress, place.latitude, place.longitude)
}