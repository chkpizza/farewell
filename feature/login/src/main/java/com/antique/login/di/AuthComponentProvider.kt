package com.antique.login.di

import com.antique.login.di.component.AuthComponent

interface AuthComponentProvider {
    fun provideAuthComponent(): AuthComponent
}