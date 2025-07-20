package com.example.settings.presentation

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.example.common.store.AppTheme
import com.example.common.store.NamedStore
import javax.inject.Inject

class SettingsViewmodel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    val isDarkTheme: Boolean
        get() = sharedPreferences.getBoolean(NamedStore.DARK_THEME, false)

    fun setTheme(isDark: Boolean) {
        sharedPreferences.edit { putBoolean(NamedStore.DARK_THEME, isDark) }
    }

    fun setPrimaryColor(newColor: AppTheme.PrimaryColorVariant) {
        sharedPreferences.edit { putString(NamedStore.PRIMARY_COLOR, newColor.name) }
    }
}