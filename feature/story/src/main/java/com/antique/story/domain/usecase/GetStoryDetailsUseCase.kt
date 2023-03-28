package com.antique.story.domain.usecase

import com.antique.story.data.model.story.story.Story
import com.antique.story.data.model.story.story.StoryUiState
import com.antique.story.data.repo.StoryRepository
import javax.inject.Inject

class GetStoryDetailsUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke(storyId: String) = mapper(storyRepository.getStory(storyId))

    private fun mapper(story: Story): StoryUiState {
        return StoryUiState(story.body, story.contents, story.place, story.date, story.storyId)
    }
}