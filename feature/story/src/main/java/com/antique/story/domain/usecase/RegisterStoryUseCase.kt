package com.antique.story.domain.usecase

import com.antique.story.domain.repository.StoryRepository
import javax.inject.Inject

class RegisterStoryUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke(body: String, pictures: List<String>, videos: List<String>, date: String) = storyRepository.registerStory(body, pictures, videos, date)
}