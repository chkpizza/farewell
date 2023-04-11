package com.antique.story.data.repository

import com.antique.story.data.mapper.mapperToDomain
import com.antique.story.data.model.Meta
import com.antique.story.data.model.PlaceDto
import com.antique.story.data.retrofit.PlaceApiService
import com.antique.story.domain.model.Place
import com.antique.story.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeApiService: PlaceApiService,
    private val dispatcher: CoroutineDispatcher
) : PlaceRepository {
    override suspend fun getPlaces(key: String, query: String, page: Int): Place = withContext(dispatcher) {
        val response = placeApiService.getPlaceByKeyword(key, query, page)
        if(response.isSuccessful) {
            response.body()?.let {
                mapperToDomain(it)
            } ?: throw RuntimeException()
        } else {
            throw RuntimeException()
        }
    }
}