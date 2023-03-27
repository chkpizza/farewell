package com.antique.settings.di.module

import androidx.lifecycle.ViewModel
import com.antique.common.di.ViewModelKey
import com.antique.settings.viewmodel.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SettingsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel
}