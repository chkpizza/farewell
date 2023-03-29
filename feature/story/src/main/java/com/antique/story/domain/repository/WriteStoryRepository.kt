package com.antique.story.domain.repository

import com.antique.story.data.model.place.PlaceResponse
import com.antique.story.domain.model.Content
import com.antique.story.data.model.StoryDto
import com.antique.story.domain.model.Place

interface WriteStoryRepository {
    suspend fun getLocations(key: String, query: String, page: Int): PlaceResponse
    suspend fun registerStory(body: String, contents: List<Content>, place: Place, date: String): StoryDto
}