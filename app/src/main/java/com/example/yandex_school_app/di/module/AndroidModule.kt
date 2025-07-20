package com.example.yandex_school_app.di.module

import android.content.Context
import androidx.work.WorkManager
import com.example.yandex_school_app.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
object AndroidModule {
    @Provides
    @ApplicationScope
    fun provideWorkManager(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}