package com.antique.story.di.module

import androidx.lifecycle.ViewModel
import com.antique.common.di.ViewModelKey
import com.antique.story.viewmodel.StoryViewModel
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