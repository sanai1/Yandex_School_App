package com.example.yandex_school_app.di

import android.content.Context
import androidx.work.WorkManager
import com.example.yandex_school_app.di.module.NetworkModule
import com.example.yandex_school_app.MainActivity
import com.example.yandex_school_app.di.module.AndroidModule
import com.example.yandex_school_app.di.module.DataModule
import com.example.yandex_school_app.di.module.DatabaseModule
import com.example.yandex_school_app.di.module.DomainModule
import com.example.yandex_school_app.di.module.ViewModelModule
import com.example.yandex_school_app.sync.SynchronizedCustom
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        AndroidModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
    fun workManager(): WorkManager
    fun synchronizedCustom(): SynchronizedCustom

    @Component.Factory
    interface ComponentFactory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}