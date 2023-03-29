package com.antique.story.domain.usecase

import com.antique.story.data.model.StoryDto
import com.antique.story.domain.model.Story
import com.antique.story.domain.repository.StoryRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke() = mapper(storyRepository.getStories().reversed())

    private fun mapper(stories: List<StoryDto>): List<Story> {
        val uiStates = mutableListOf<Story>()
        stories.forEach {
            uiStates.add(Story(it.body, it.contents, it.place, it.date, it.storyId))
        }
        return uiStates.toList()
    }
}