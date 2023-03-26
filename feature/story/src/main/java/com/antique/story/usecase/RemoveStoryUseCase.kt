package com.antique.story.usecase

import com.antique.story.repo.StoryRepository
import javax.inject.Inject

class RemoveStoryUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke(storyId: String) = storyRepository.removeStory(storyId)
}