package com.antique.story.data.model.place

import androidx.annotation.Keep

@Keep
data class PlaceResponse(
    val documents: List<Document>,
    val meta: Meta
)

@Keep
data class SameName(
    val keyword: String,
    val region: List<Any>,
    val selected_region: String
)

@Keep
data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val same_name: SameName,
    val total_count: Int
)

@Keep
data class Document(
    val address_name: String,
    val category_group_code: String,
    val category_group_name: String,
    val category_name: String,
    val distance: String,
    val id: String,
    val phone: String,
    val place_name: String,
    val place_url: String,
    val road_address_name: String,
    val x: String,
    val y: String
)