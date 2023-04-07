package com.antique.story.data.mapper

import com.antique.story.data.model.PlaceDto
import com.antique.story.data.model.PlaceInformationDto
import com.antique.story.domain.model.Place
import com.antique.story.domain.model.PlaceInformation

fun mapperToDomain(placeDto: PlaceDto): Place {
    val placeInformation = mutableListOf<PlaceInformation>()

    placeDto.documents.forEach {
        placeInformation.add(PlaceInformation(it.place_name, it.address_name, it.y.toDouble(), it.x.toDouble()))
    }
    return Place(placeInformation, placeDto.meta.is_end)
}

fun mapperToDto(place: PlaceInformation): PlaceInformationDto {
    return PlaceInformationDto(place.placeName, place.placeAddress, place.latitude, place.longitude)
}