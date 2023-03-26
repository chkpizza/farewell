package com.antique.story.usecase

import com.antique.story.data.story.story.Story
import com.antique.story.data.story.story.StoryUiState
import com.antique.story.repo.StoryRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke() = mapper(storyRepository.getStories().reversed())

    private fun mapper(stories: List<Story>): List<StoryUiState> {
        val uiStates = mutableListOf<StoryUiState>()
        stories.forEach {
            uiStates.add(StoryUiState(it.body, it.contents, it.place, it.date, it.storyId))
        }
        return uiStates.toList()
    }
}