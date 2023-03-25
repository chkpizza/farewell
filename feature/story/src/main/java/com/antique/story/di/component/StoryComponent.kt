package com.antique.story.di.component

import com.antique.story.di.module.StoryViewModelModule
import com.antique.story.di.module.WriteStoryViewModelModule
import com.antique.story.view.PhotoGalleryFragment
import com.antique.story.view.SearchLocationFragment
import com.antique.story.view.VideoGalleryFragment
import com.antique.story.view.WriteStoryFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        StoryViewModelModule::class,
        WriteStoryViewModelModule::class
    ]
)
interface StoryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): StoryComponent
    }

    fun inject(writeStoryFragment: WriteStoryFragment)
    fun inject(photoGalleryFragment: PhotoGalleryFragment)
    fun inject(videoGalleryFragment: VideoGalleryFragment)
    fun inject(searchLocationFragment: SearchLocationFragment
    )
}