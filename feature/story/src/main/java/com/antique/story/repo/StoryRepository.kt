package com.antique.story.repo

import com.antique.story.data.story.door.Door
import com.antique.story.data.story.story.Story

interface StoryRepository {
    suspend fun getStories(): List<Story>
    suspend fun getMoreStories(idx: String): List<Story>
    suspend fun getDoor(): Door
    suspend fun getStory(storyId: String): Story
    suspend fun removeStory(storyId: String): Boolean
}