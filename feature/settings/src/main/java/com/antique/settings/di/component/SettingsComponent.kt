package com.antique.settings.di.component

import com.antique.settings.di.module.SettingsViewModelModule
import com.antique.settings.presentation.view.EditProfileFragment
import dagger.Subcomponent

@Subcomponent(modules = [SettingsViewModelModule::class])
interface SettingsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingsComponent
    }

    fun inject(editProfileFragment: EditProfileFragment)
}