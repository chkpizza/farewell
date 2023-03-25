package com.antique.story.repo

import com.antique.story.data.place.PlaceResponse
import com.antique.story.retrofit.PlaceApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WriteStoryRepositoryImpl @Inject constructor(
    private val apiService: PlaceApiService,
    private val dispatcher: CoroutineDispatcher
) : WriteStoryRepository {
    override suspend fun getLocations(key: String, query: String, page: Int): PlaceResponse = withContext(dispatcher) {
        val response = apiService.getPlaceByKeyword(key, query, page)
        if(response.isSuccessful) {
            response.body() ?: throw RuntimeException("body is null")
        } else {
            throw RuntimeException("not successful")
        }
    }
}