package com.antique.settings.di

import com.antique.settings.di.component.SettingsComponent

interface SettingsComponentProvider {
    fun provideSettingsComponent(): SettingsComponent
}