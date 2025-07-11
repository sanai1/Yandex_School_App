package com.example.common.store

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object ThemeStore {
    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(APP_THEME, Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean(IS_FIRST_RUN, true)) {
            sharedPreferences.edit {
                putBoolean(IS_FIRST_RUN, false).putBoolean(DARK_THEME, false)
            }
        }
    }

    var isDarkTheme: Boolean
        get() = sharedPreferences.getBoolean(DARK_THEME, false)
        set(value) {
            sharedPreferences.edit { putBoolean(DARK_THEME, value) }
        }
    private const val APP_THEME = "app_theme"
    private const val DARK_THEME = "dark_theme"
    private const val IS_FIRST_RUN = "is_first_run"
}