package com.antique.story.di

import com.antique.story.di.component.StoryComponent

interface StoryComponentProvider {
    fun provideStoryComponent(): StoryComponent
}