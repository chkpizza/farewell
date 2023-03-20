package com.antique.farewell.auth.repo

interface AuthRepository {
    suspend fun isExistUserUseCase(uid: String): Boolean
    suspend fun isWithdrawnUser(uid: String): Boolean
}