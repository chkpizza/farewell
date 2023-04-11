package com.antique.story.domain.usecase

import com.antique.story.domain.repository.StoryRepository
import javax.inject.Inject

class FetchStoriesUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke(index: String = "") = storyRepository.fetchStories(index)
}