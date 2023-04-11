package com.antique.story.di

import androidx.lifecycle.ViewModel
import com.antique.common.di.ViewModelKey
import com.antique.story.presentation.view.main.StoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StoryViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(StoryViewModel::class)
    abstract fun bindStoryViewModel(storyViewModel: StoryViewModel): ViewModel
}