package com.antique.story.di.component

import com.antique.story.di.module.StoryDetailsViewModelModule
import com.antique.story.di.module.StoryViewModelModule
import com.antique.story.di.module.WriteStoryViewModelModule
import com.antique.story.presentation.view.details.StoryDetailsFragment
import com.antique.story.presentation.view.story.StoryFragment
import com.antique.story.presentation.view.photo.PhotoGalleryFragment
import com.antique.story.presentation.view.location.SearchLocationFragment
import com.antique.story.presentation.view.video.VideoGalleryFragment
import com.antique.story.presentation.view.write.WriteStoryFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        StoryViewModelModule::class,
        WriteStoryViewModelModule::class,
        StoryDetailsViewModelModule::class
    ]
)
interface StoryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): StoryComponent
    }

    fun inject(storyFragment: StoryFragment)
    fun inject(storyDetailsFragment: StoryDetailsFragment)
    fun inject(writeStoryFragment: WriteStoryFragment)
    fun inject(photoGalleryFragment: PhotoGalleryFragment)
    fun inject(videoGalleryFragment: VideoGalleryFragment)
    fun inject(searchLocationFragment: SearchLocationFragment)
}