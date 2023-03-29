package com.antique.story.di

import com.antique.story.presentation.view.add.AddStoryFragment
import com.antique.story.presentation.view.picture.PictureFragment
import com.antique.story.presentation.view.video.VideoFragment
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
    fun inject(videoFragment: VideoFragment)
}