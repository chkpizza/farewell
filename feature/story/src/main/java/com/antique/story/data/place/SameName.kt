package com.antique.story.data.place

import androidx.annotation.Keep

@Keep
data class SameName(
    val keyword: String,
    val region: List<Any>,
    val selected_region: String
)