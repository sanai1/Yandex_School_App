package com.example.yandex_school_app.di

import android.app.Application

class AppMain : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create()
    }
}