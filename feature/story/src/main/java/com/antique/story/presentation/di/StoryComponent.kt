package com.antique.story.presentation.di

import com.antique.story.presentation.view.add.AddStoryFragment
import com.antique.story.presentation.view.picture.PictureFragment
import dagger.Subcomponent

@Subcomponent(modules = [
    AddStoryViewModelModule::class
])
interface StoryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): StoryComponent
    }

    fun inject(addStoryFragment: AddStoryFragment)
    fun inject(pictureFragment: PictureFragment)
}