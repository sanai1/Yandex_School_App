package com.example.yandex_school_app.di

import com.example.yandex_school_app.MainActivity
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface ComponentFactory {
        fun create(): AppComponent
    }
}