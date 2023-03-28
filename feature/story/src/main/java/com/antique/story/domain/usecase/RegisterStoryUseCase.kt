package com.antique.story.domain.usecase

import com.antique.story.data.model.story.story.Content
import com.antique.story.data.model.story.story.Place
import com.antique.story.data.model.story.story.Story
import com.antique.story.data.model.story.story.StoryUiState
import com.antique.story.data.repo.WriteStoryRepository
import javax.inject.Inject

class RegisterStoryUseCase @Inject constructor(private val writeStoryRepository: WriteStoryRepository) {
    suspend operator fun invoke(body: String, contents: List<Content>, place: Place, date: String ) = mapper(writeStoryRepository.registerStory(body, contents, place, date))

    private fun mapper(story: Story): StoryUiState {
        return StoryUiState(story.body, story.contents, story.place, story.date, story.storyId)
    }
}