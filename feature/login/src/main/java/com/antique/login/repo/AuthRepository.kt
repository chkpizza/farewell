package com.antique.login.repo

import com.antique.common.data.User

interface AuthRepository {
    suspend fun isExistUserUseCase(uid: String): Boolean
    suspend fun isWithdrawnUser(uid: String): Boolean
    suspend fun registerUser(user: User): Boolean
    suspend fun withdrawnCancel(uid: String): Boolean
}