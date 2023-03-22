package com.antique.login.usecase

import com.antique.login.repo.AuthRepository
import javax.inject.Inject

class IsWithdrawnUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(uid: String) = authRepository.isWithdrawnUser(uid)
}