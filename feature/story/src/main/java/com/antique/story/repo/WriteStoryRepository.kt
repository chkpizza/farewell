package com.antique.story.repo

import com.antique.story.data.place.PlaceResponse
import com.antique.story.data.story.story.Content
import com.antique.story.data.story.Place
import com.antique.story.data.story.story.Story

interface WriteStoryRepository {
    suspend fun getLocations(key: String, query: String, page: Int): PlaceResponse
    suspend fun registerStory(body: String, contents: List<Content>, place: Place, date: String): Story
}