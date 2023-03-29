package com.antique.story.domain.repository

interface StoryRepository {
    suspend fun registerStory(body: String, pictures: List<String>, videos: List<String>, date: String): Boolean
}