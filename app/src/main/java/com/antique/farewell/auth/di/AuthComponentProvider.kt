package com.antique.farewell.auth.di

import com.antique.farewell.auth.di.component.AuthComponent

interface AuthComponentProvider {
    fun provideAuthComponent(): AuthComponent
}