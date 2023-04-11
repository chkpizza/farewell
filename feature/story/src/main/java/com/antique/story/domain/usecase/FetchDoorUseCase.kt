package com.antique.story.domain.usecase

import com.antique.story.domain.model.Door
import com.antique.story.domain.repository.StoryRepository
import javax.inject.Inject

class FetchDoorUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke(): Door = storyRepository.fetchDoor()
}