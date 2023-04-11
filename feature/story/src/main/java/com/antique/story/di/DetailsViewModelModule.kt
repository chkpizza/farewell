package com.antique.story.di

import androidx.lifecycle.ViewModel
import com.antique.common.di.ViewModelKey
import com.antique.story.presentation.view.details.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel
}