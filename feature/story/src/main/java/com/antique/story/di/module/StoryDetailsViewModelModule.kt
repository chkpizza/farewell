package com.antique.story.di.module

import androidx.lifecycle.ViewModel
import com.antique.common.di.ViewModelKey
import com.antique.story.presentation.viewmodel.StoryDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StoryDetailsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(StoryDetailsViewModel::class)
    abstract fun bindStoryDetailsViewModel(storyDetailsViewModel: StoryDetailsViewModel): ViewModel
}