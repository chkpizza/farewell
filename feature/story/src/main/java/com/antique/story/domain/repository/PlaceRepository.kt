package com.antique.story.domain.repository

import com.antique.story.data.model.PlaceDto
import com.antique.story.domain.model.Place

interface PlaceRepository {
    suspend fun getPlaces(key: String, query: String, page: Int): Place
}