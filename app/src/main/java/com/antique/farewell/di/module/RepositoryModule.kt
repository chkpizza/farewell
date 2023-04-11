package com.antique.farewell.di.module

import com.antique.information.data.repository.InformationRepositoryImpl
import com.antique.information.domain.repository.InformationRepository
import com.antique.login.domain.repository.AuthRepository
import com.antique.login.data.repository.AuthRepositoryImpl
import com.antique.settings.data.repo.SettingsRepository
import com.antique.settings.data.repo.SettingsRepositoryImpl
import com.antique.story.data.repository.PlaceRepositoryImpl
import com.antique.story.data.repository.StoryRepositoryImpl
import com.antique.story.data.retrofit.PlaceApiService
import com.antique.story.domain.repository.PlaceRepository
import com.antique.story.domain.repository.StoryRepository
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
    fun provideSettingsRepository(dispatcher: CoroutineDispatcher): SettingsRepository {
        return SettingsRepositoryImpl(dispatcher)
    }

    @Singleton
    @Provides
    fun provideStoryRepository(dispatcher: CoroutineDispatcher): StoryRepository {
        return StoryRepositoryImpl(dispatcher)
    }

    @Singleton
    @Provides
    fun providePlaceRepository(placeApiService: PlaceApiService, dispatcher: CoroutineDispatcher): PlaceRepository {
        return PlaceRepositoryImpl(placeApiService, dispatcher)
    }

    @Singleton
    @Provides
    fun provideInformationRepository(dispatcher: CoroutineDispatcher): InformationRepository {
        return InformationRepositoryImpl(dispatcher)
    }
}