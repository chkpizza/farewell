package com.antique.story.domain.usecase

import com.antique.story.domain.repository.StoryRepository
import javax.inject.Inject

class FetchStoryUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke(id: String) = storyRepository.fetchStory(id)
}