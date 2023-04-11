package com.antique.information.di

import com.antique.information.presentation.view.information.InformationFragment
import dagger.Subcomponent

@Subcomponent(modules = [
    InformationViewModelModule::class
])
interface InformationComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): InformationComponent
    }

    fun inject(informationFragment: InformationFragment)
}