package com.antique.story.domain.repository

import com.antique.story.data.model.DoorDto
import com.antique.story.data.model.StoryDto

interface StoryRepository {
    suspend fun getStories(): List<StoryDto>
    suspend fun getMoreStories(idx: String): List<StoryDto>
    suspend fun getDoor(): DoorDto
    suspend fun getStory(storyId: String): StoryDto
    suspend fun removeStory(storyId: String): Boolean
}