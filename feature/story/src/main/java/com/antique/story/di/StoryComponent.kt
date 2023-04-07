package com.antique.story.di

import com.antique.story.presentation.view.add.AddStoryFragment
import com.antique.story.presentation.view.main.StoryFragment
import com.antique.story.presentation.view.picture.PictureFragment
import com.antique.story.presentation.view.place.PlaceFragment
import com.antique.story.presentation.view.video.VideoFragment
import dagger.Subcomponent

@Subcomponent(modules = [
    AddStoryViewModelModule::class,
    PlaceViewModelModule::class,
    StoryViewModelModule::class
])
interface StoryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): StoryComponent
    }

    fun inject(addStoryFragment: AddStoryFragment)
    fun inject(pictureFragment: PictureFragment)
    fun inject(videoFragment: VideoFragment)
    fun inject(placeFragment: PlaceFragment)
    fun inject(storyFragment: StoryFragment)
}