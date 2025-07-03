package com.example.yandex_school_app.di

import android.content.Context
import com.example.yandex_school_app.di.ApplicationScope
import com.example.yandex_school_app.di.module.NetworkModule
import com.example.yandex_school_app.MainActivity
import com.example.yandex_school_app.di.module.DataModule
import com.example.yandex_school_app.di.module.DomainModule
import com.example.yandex_school_app.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, ViewModelModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface ComponentFactory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}