package com.antique.story.repo

import com.antique.story.data.place.PlaceResponse

interface WriteStoryRepository {
    suspend fun getLocations(key: String, query: String, page: Int): PlaceResponse
}