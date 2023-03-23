package com.antique.farewell.di.module

import androidx.lifecycle.ViewModelProvider
import com.antique.common.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}