package com.antique.story.di.module

import androidx.lifecycle.ViewModel
import com.antique.common.di.ViewModelKey
import com.antique.story.presentation.viewmodel.WriteStoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WriteStoryViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WriteStoryViewModel::class)
    abstract fun bindWriteStoryViewModel(writeStoryViewModel: WriteStoryViewModel): ViewModel
}