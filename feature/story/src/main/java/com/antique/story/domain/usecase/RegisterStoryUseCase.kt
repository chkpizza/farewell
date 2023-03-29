package com.antique.story.domain.usecase

import com.antique.story.domain.model.Content
import com.antique.story.data.model.StoryDto
import com.antique.story.domain.model.Place
import com.antique.story.domain.model.Story
import com.antique.story.domain.repository.WriteStoryRepository
import javax.inject.Inject

class RegisterStoryUseCase @Inject constructor(private val writeStoryRepository: WriteStoryRepository) {
    suspend operator fun invoke(body: String, contents: List<Content>, place: Place, date: String ) = mapper(writeStoryRepository.registerStory(body, contents, place, date))

    private fun mapper(storyDto: StoryDto): Story {
        return Story(storyDto.body, storyDto.contents, storyDto.place, storyDto.date, storyDto.storyId)
    }
}