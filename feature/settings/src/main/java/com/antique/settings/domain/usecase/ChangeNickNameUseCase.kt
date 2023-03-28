package com.antique.settings.domain.usecase

import com.antique.settings.data.repo.SettingsRepository
import javax.inject.Inject

class ChangeNickNameUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(nickName: String) = settingsRepository.changeNickName(nickName)
}