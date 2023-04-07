package com.antique.story.domain.repository

import com.antique.story.domain.model.PlaceInformation

interface StoryRepository {
    suspend fun registerStory(body: String, pictures: List<String>, videos: List<String>, place: PlaceInformation, date: String): Boolean
}