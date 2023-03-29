package com.antique.story.domain.usecase

import com.antique.story.data.model.DoorDto
import com.antique.story.domain.model.Door
import com.antique.story.domain.repository.StoryRepository
import javax.inject.Inject

class GetDoorUseCase @Inject constructor(private val storyRepository: StoryRepository) {
    suspend operator fun invoke() = mapper(storyRepository.getDoor())

    private fun mapper(doorDto: DoorDto): Door {
        return Door(doorDto.nickName, doorDto.doorText)
    }
}