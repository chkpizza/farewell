package com.antique.login.data.repo

import com.antique.common.data.User
import com.antique.common.util.Constant
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher) :
    AuthRepository {
    override suspend fun isExistUserUseCase(uid: String): Boolean = withContext(dispatcher) {
        Firebase.database.reference.child(Constant.USER_NODE).child(uid).get().await().exists()
    }

    override suspend fun isWithdrawnUser(uid: String): Boolean = withContext(dispatcher) {
        Firebase.database.reference.child(Constant.WITHDRAWN_NODE).child(uid).get().await().exists()
    }

    override suspend fun registerUser(user: User): Boolean = withContext(dispatcher) {
        Firebase.database.reference.child(Constant.USER_NODE).child(user.uid).setValue(user).await()
        Firebase.database.reference.child(Constant.USER_NODE).child(user.uid).get().await().exists()
    }

    override suspend fun withdrawnCancel(uid: String): Boolean = withContext(dispatcher) {
        Firebase.database.reference.child(Constant.WITHDRAWN_NODE).child(uid).setValue(null).await()
        Firebase.database.reference.child(Constant.WITHDRAWN_NODE).child(uid).get().await().exists().not()
    }
}