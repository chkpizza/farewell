package com.antique.story.domain.usecase

import com.antique.story.data.model.Place
import com.antique.story.data.model.place.PlaceResponse
import com.antique.story.domain.model.Place
import com.antique.story.domain.repository.WriteStoryRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val storyRepository: WriteStoryRepository) {
    suspend operator fun invoke(key: String, query: String, page: Int) = mapper(storyRepository.getLocations(key, query, page))

    private fun mapper(response: PlaceResponse): Place {
        val placesInformation = mutableListOf<com.antique.story.data.model.Place>()
        response.documents.forEach {
            placesInformation.add(com.antique.story.data.model.Place(it.place_name, it.address_name, it.y.toDouble(), it.x.toDouble()))
        }
        return Place(placesInformation.toList(), response.meta.is_end)
    }
}