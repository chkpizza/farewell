package com.antique.settings.data.repo

import com.antique.common.data.UserDto
import com.antique.common.util.Constant
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher) :
    SettingsRepository {
    override suspend fun changeNickName(nickName: String): Boolean = withContext(dispatcher) {
        val uid = Firebase.auth.currentUser?.uid.toString()
        Firebase.database.reference.child(Constant.USER_NODE).child(uid).child("nickName").setValue(nickName).await()
        Firebase.database.reference.child(Constant.USER_NODE).child(uid).get().await().getValue(UserDto::class.java)?.let {
            it.nickName == nickName
        } ?: throw RuntimeException()
    }
}