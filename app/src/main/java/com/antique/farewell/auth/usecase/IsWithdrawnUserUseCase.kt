package com.antique.farewell.auth.usecase

import com.antique.farewell.auth.repo.AuthRepository
import javax.inject.Inject

class IsWithdrawnUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(uid: String) = authRepository.isWithdrawnUser(uid)
}