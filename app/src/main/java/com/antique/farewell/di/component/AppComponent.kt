package com.antique.farewell.di.component

import com.antique.farewell.di.module.RepositoryModule
import com.antique.farewell.di.module.RetrofitModule
import com.antique.farewell.di.module.ViewModelFactoryModule
import com.antique.login.di.component.AuthComponent
import com.antique.settings.di.component.SettingsComponent
import com.antique.story.presentation.di.StoryComponent
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SubcomponentsModule::class,
        ViewModelFactoryModule::class,
        RepositoryModule::class,
        RetrofitModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun getAuthComponent(): AuthComponent.Factory
    fun getSettingsComponent(): SettingsComponent.Factory
    fun getStoryComponent(): StoryComponent.Factory
}

@Module(
    subcomponents = [
        AuthComponent::class,
        SettingsComponent::class,
        StoryComponent::class
    ]
)
object SubcomponentsModule