package com.antique.story.domain.usecase

import com.antique.story.data.model.StoryDto
import com.antique.story.domain.model.Story
import com.antique.story.domain.repository.StoryRepository
import javax.inject.Inject

class GetStoryDetailsUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke(storyId: String) = mapper(storyRepository.getStory(storyId))

    private fun mapper(storyDto: StoryDto): Story {
        return Story(storyDto.body, storyDto.contents, storyDto.place, storyDto.date, storyDto.storyId)
    }
}