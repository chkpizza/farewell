package com.antique.story.usecase

import com.antique.story.data.story.story.Story
import com.antique.story.data.story.story.StoryUiState
import com.antique.story.repo.StoryRepository
import javax.inject.Inject

class GetStoryDetailsUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke(storyId: String) = mapper(storyRepository.getStory(storyId))

    private fun mapper(story: Story): StoryUiState {
        return StoryUiState(story.body, story.contents, story.place, story.date, story.storyId)
    }
}