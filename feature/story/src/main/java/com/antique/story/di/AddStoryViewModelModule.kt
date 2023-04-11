package com.antique.story.di

import androidx.lifecycle.ViewModel
import com.antique.common.di.ViewModelKey
import com.antique.story.presentation.view.add.AddStoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddStoryViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddStoryViewModel::class)
    abstract fun bindAddStoryViewModel(addStoryViewModel: AddStoryViewModel): ViewModel
}