package com.antique.farewell.application

import android.app.Application
import com.antique.farewell.di.component.AppComponent
import com.antique.farewell.di.component.DaggerAppComponent
import com.antique.login.di.AuthComponentProvider
import com.antique.login.di.component.AuthComponent
import com.antique.settings.di.SettingsComponentProvider
import com.antique.settings.di.component.SettingsComponent
import com.antique.story.di.StoryComponentProvider
import com.antique.story.di.component.StoryComponent

class FarewellApplication : Application(), AuthComponentProvider, StoryComponentProvider, SettingsComponentProvider {
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

    override fun provideSettingsComponent(): SettingsComponent {
        return appComponent.getSettingsComponent().create()
    }


}