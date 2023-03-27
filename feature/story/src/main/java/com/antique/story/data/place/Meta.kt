package com.antique.story.data.place

import androidx.annotation.Keep

@Keep
data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val same_name: SameName,
    val total_count: Int
)