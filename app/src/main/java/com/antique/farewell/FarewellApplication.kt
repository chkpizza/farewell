package com.antique.farewell

import android.app.Application
import com.antique.farewell.auth.di.AuthComponentProvider
import com.antique.farewell.auth.di.component.AuthComponent
import com.antique.farewell.di.component.AppComponent
import com.antique.farewell.di.component.DaggerAppComponent

class FarewellApplication : Application(), AuthComponentProvider {
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
}