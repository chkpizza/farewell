package com.antique.farewell.di.module

import com.antique.login.data.repo.AuthRepository
import com.antique.login.data.repo.AuthRepositoryImpl
import com.antique.settings.data.repo.SettingsRepository
import com.antique.settings.data.repo.SettingsRepositoryImpl
import com.antique.story.domain.repository.StoryRepository
import com.antique.story.data.repository.StoryRepositoryImpl
import com.antique.story.domain.repository.WriteStoryRepository
import com.antique.story.data.repository.WriteStoryRepositoryImpl
import com.antique.story.data.retrofit.PlaceApiService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun provideAuthRepository(dispatcher: CoroutineDispatcher): AuthRepository {
        return AuthRepositoryImpl(dispatcher)
    }

    @Singleton
    @Provides
    fun provideWriteStoryRepository(apiService: PlaceApiService, dispatcher: CoroutineDispatcher): WriteStoryRepository {
        return WriteStoryRepositoryImpl(apiService, dispatcher)
    }

    @Singleton
    @Provides
    fun provideStoryRepository(dispatcher: CoroutineDispatcher): StoryRepository {
        return StoryRepositoryImpl(dispatcher)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(dispatcher: CoroutineDispatcher): SettingsRepository {
        return SettingsRepositoryImpl(dispatcher)
    }
}