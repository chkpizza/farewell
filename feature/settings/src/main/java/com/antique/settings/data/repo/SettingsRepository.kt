package com.antique.settings.data.repo

interface SettingsRepository {
    suspend fun changeNickName(nickName: String): Boolean
}