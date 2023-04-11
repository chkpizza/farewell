package com.antique.farewell.application

import android.app.Application
import com.antique.farewell.di.component.AppComponent
import com.antique.farewell.di.component.DaggerAppComponent
import com.antique.information.di.InformationComponent
import com.antique.information.di.InformationComponentProvider
import com.antique.login.di.AuthComponentProvider
import com.antique.login.di.component.AuthComponent
import com.antique.settings.di.SettingsComponentProvider
import com.antique.settings.di.component.SettingsComponent
import com.antique.story.di.StoryComponent
import com.antique.story.di.StoryComponentProvider

class FarewellApplication : Application(), AuthComponentProvider, SettingsComponentProvider,
    StoryComponentProvider, InformationComponentProvider {
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

    override fun provideSettingsComponent(): SettingsComponent {
        return appComponent.getSettingsComponent().create()
    }

    override fun provide(): StoryComponent {
        return appComponent.getStoryComponent().create()
    }

    override fun provideInformationComponent(): InformationComponent {
        return appComponent.getInformationComponent().create()
    }


}