package com.antique.story.usecase

import com.antique.story.data.place.PlaceResponse
import com.antique.story.data.story.Place
import com.antique.story.data.story.PlaceUiState
import com.antique.story.repo.WriteStoryRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val storyRepository: WriteStoryRepository) {
    suspend operator fun invoke(key: String, query: String, page: Int) = mapper(storyRepository.getLocations(key, query, page))

    private fun mapper(response: PlaceResponse): PlaceUiState {
        val placesInformation = mutableListOf<Place>()
        response.documents.forEach {
            placesInformation.add(Place(it.place_name, it.address_name, it.y.toDouble(), it.x.toDouble()))
        }
        return PlaceUiState(placesInformation.toList(), response.meta.is_end)
    }
}