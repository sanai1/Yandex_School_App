package com.example.common.store

import android.content.SharedPreferences
import androidx.core.content.edit

object ThemeStore {
    private lateinit var sharedPreferences: SharedPreferences
    fun setSharedPreferences(sharPref: SharedPreferences) {
        sharedPreferences = sharPref
        if (sharedPreferences.getBoolean(IS_FIRST_RUN, true)) {
            sharedPreferences.edit { putBoolean(IS_FIRST_RUN, false).putBoolean(DARK_THEME, false) }
        }
    }

    var isDarkTheme: Boolean
        get() = sharedPreferences.getBoolean(DARK_THEME, false)
        set(value) {
            sharedPreferences.edit { putBoolean(DARK_THEME, value) }
        }
    private const val DARK_THEME = "dark_theme"
    private const val IS_FIRST_RUN = "is_first_run"
}