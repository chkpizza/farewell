package com.antique.information.domain.repository

import com.antique.information.domain.model.Preview

interface InformationRepository {
    suspend fun fetchPreview(): List<Preview>
}