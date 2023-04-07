package com.antique.story.data.retrofit

import com.antique.story.data.model.PlaceDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PlaceApiService {
    @GET("/v2/local/search/keyword.json")
    suspend fun getPlaceByKeyword(
        @Header("Authorization") key: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<PlaceDto>
}