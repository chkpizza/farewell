package com.antique.login.di.component

import com.antique.login.di.module.AuthViewModelModule
import com.antique.login.view.AuthFragment
import com.antique.login.view.OnBoardingFragment
import com.antique.login.view.SetupFragment
import com.antique.login.view.WithdrawnCancelFragment
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