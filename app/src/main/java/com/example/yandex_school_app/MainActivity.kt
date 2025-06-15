package com.example.yandex_school_app

import android.os.Bundle
import androidx.compose.runtime.remember
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import com.example.yandex_school_app.di.AppMain
import com.example.yandex_school_app.di.DaggerViewModelFactory
import com.example.yandex_school_app.navigation.App
import com.example.yandex_school_app.ui.theme.Yandex_School_AppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AppMain).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yandex_School_AppTheme {
                val showSplash = remember { mutableStateOf(true) }
                if (showSplash.value) {
                    SplashScreen {
                        showSplash.value = false
                    }
                } else {
                    App()
                }
            }
        }
    }
}