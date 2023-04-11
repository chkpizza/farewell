package com.antique.information.di

import androidx.lifecycle.ViewModel
import com.antique.common.di.ViewModelKey
import com.antique.information.presentation.view.information.InformationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class InformationViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(InformationViewModel::class)
    abstract fun bindInformationViewModel(informationViewModel: InformationViewModel): ViewModel
}