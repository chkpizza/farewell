package com.antique.login.domain.usecase

import com.antique.login.domain.repository.AuthRepository
import javax.inject.Inject

class IsWithdrawnUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(uid: String) = authRepository.isWithdrawnUser(uid)
}