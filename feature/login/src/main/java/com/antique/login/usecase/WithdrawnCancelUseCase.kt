package com.antique.login.usecase

import com.antique.login.repo.AuthRepository
import javax.inject.Inject

class WithdrawnCancelUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(uid: String): Boolean = authRepository.withdrawnCancel(uid)
}