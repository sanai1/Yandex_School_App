package com.example.yandex_school_app

import android.os.Bundle
import androidx.compose.runtime.remember
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import com.example.yandex_school_app.navigation.App
import com.example.yandex_school_app.ui.theme.Yandex_School_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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