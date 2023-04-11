package com.antique.login.domain.usecase

import com.antique.common.data.UserDto
import com.antique.common.data.User
import com.antique.login.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(user: User): Boolean = authRepository.registerUser(user)
}