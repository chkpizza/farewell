package com.antique.farewell.application

import android.app.Application
import com.antique.farewell.di.component.AppComponent
import com.antique.farewell.di.component.DaggerAppComponent
import com.antique.login.di.AuthComponentProvider
import com.antique.login.di.component.AuthComponent
import com.antique.story.di.StoryComponentProvider
import com.antique.story.di.component.StoryComponent

class FarewellApplication : Application(), AuthComponentProvider, StoryComponentProvider {
    val appComponent by lazy { initAppComponent() }

    private fun initAppComponent(): AppComponent {
        return DaggerAppComponent.create()
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun provideAuthComponent(): AuthComponent {
        return appComponent.getAuthComponent().create()
    }

    override fun provideStoryComponent(): StoryComponent {
        return appComponent.getStoryComponent().create()
    }


}