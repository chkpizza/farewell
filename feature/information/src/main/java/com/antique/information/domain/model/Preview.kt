package com.antique.information.domain.model

data class Preview(
    val imageUrl: String,
    val name: String,
    val address: String,
    val free: Boolean,
    val period: String
)
