package com.antique.login.di.component

import com.antique.login.di.module.AuthViewModelModule
import com.antique.login.presentation.view.AuthFragment
import com.antique.login.presentation.view.OnBoardingFragment
import com.antique.login.presentation.view.SetupFragment
import com.antique.login.presentation.view.WithdrawnCancelFragment
import dagger.Subcomponent

@Subcomponent(modules = [AuthViewModelModule::class])
interface AuthComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(onBoardingFragment: OnBoardingFragment)
    fun inject(authFragment: AuthFragment)
    fun inject(setupFragment: SetupFragment)
    fun inject(withdrawnCancelFragment: WithdrawnCancelFragment)
}