package com.antique.story.domain.repository

import com.antique.story.domain.model.Door
import com.antique.story.domain.model.PlaceInformation
import com.antique.story.domain.model.Story

interface StoryRepository {
    suspend fun registerStory(body: String, pictures: List<String>, videos: List<String>, place: PlaceInformation, date: String): Story
    suspend fun fetchStories(index: String): List<Story>
    suspend fun fetchDoor(): Door
    suspend fun fetchStory(id: String): Story
    suspend fun removeStory(id: String): Boolean
}