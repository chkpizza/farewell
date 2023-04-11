package com.antique.information.data.mapper

import com.antique.information.data.model.PreviewDto
import com.antique.information.domain.model.Preview

fun mapperToDomain(previewDto: PreviewDto): Preview {
    return Preview(
        previewDto.imageUrl ?: "",
        previewDto.name ?: "",
        previewDto.address ?: "",
        previewDto.free ?: false,
        previewDto.period ?: ""
    )
}