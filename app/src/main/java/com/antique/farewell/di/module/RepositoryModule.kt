package com.antique.farewell.di.module

import com.antique.login.repo.AuthRepository
import com.antique.login.repo.AuthRepositoryImpl
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
}