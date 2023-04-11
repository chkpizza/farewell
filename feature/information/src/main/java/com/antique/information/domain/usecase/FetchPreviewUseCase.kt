package com.antique.information.domain.usecase

import com.antique.information.domain.model.Preview
import com.antique.information.domain.repository.InformationRepository
import javax.inject.Inject

class FetchPreviewUseCase @Inject constructor(private val informationRepository: InformationRepository) {
    suspend operator fun invoke(): List<Preview> = informationRepository.fetchPreview()
}