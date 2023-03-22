package com.antique.login.usecase

import com.antique.common.data.User
import com.antique.common.data.UserUiState
import com.antique.login.repo.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(user: UserUiState): Boolean = authRepository.registerUser(mapper(user))

    fun mapper(user: UserUiState): User {
        return User(user.uid, user.nickName, user.profileImageUrl, user.date)
    }
}