package com.antique.farewell.auth.di.component

import com.antique.farewell.auth.di.module.AuthViewModelModule
import com.antique.farewell.auth.view.AuthFragment
import dagger.Subcomponent

@Subcomponent(modules = [AuthViewModelModule::class])
interface AuthComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(authFragment: AuthFragment)
}