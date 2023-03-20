package com.antique.farewell.auth.repo

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher) : AuthRepository {
    override suspend fun isExistUserUseCase(uid: String): Boolean = withContext(dispatcher) {
        Firebase.database.reference.child("User").child(uid).get().await().exists()
    }

    override suspend fun isWithdrawnUser(uid: String): Boolean = withContext(dispatcher) {
        Firebase.database.reference.child("Withdrawn").child(uid).get().await().exists()
    }
}