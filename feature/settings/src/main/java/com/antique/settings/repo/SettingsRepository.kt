package com.antique.settings.repo

interface SettingsRepository {
    suspend fun changeNickName(nickName: String): Boolean
}