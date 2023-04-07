package com.antique.story.domain.usecase

import com.antique.story.domain.repository.PlaceRepository
import javax.inject.Inject

class FetchPlacesUseCase @Inject constructor(private val placeRepository: PlaceRepository) {
    suspend operator fun invoke(key: String, query: String, page: Int) = placeRepository.getPlaces(key, query, page)
}