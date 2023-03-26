package com.antique.story.usecase

import com.antique.story.data.story.door.Door
import com.antique.story.data.story.door.DoorUiState
import com.antique.story.repo.StoryRepository
import javax.inject.Inject

class GetDoorUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke() = mapper(storyRepository.getDoor())

    private fun mapper(door: Door): DoorUiState {
        return DoorUiState(door.nickName, door.doorText)
    }
}