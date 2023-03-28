package com.antique.story.data.repo

import com.antique.story.data.model.place.Place
import com.antique.story.data.model.place.PlaceResponse
import com.antique.story.data.model.story.story.Content
import com.antique.story.data.model.story.story.Story

interface WriteStoryRepository {
    suspend fun getLocations(key: String, query: String, page: Int): PlaceResponse
    suspend fun registerStory(body: String, contents: List<Content>, place: Place, date: String): Story
}