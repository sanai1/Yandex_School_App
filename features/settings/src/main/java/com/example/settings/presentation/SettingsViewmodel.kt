package com.example.settings.presentation

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.example.common.store.AppTheme
import com.example.common.store.NamedStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SettingsViewmodel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private var _isDarkTheme =
        MutableStateFlow(sharedPreferences.getBoolean(NamedStore.DARK_THEME, false))
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    fun setTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        sharedPreferences.edit { putBoolean(NamedStore.DARK_THEME, isDark) }
    }

    fun setPrimaryColor(newColor: AppTheme.PrimaryColorVariant) {
        sharedPreferences.edit { putString(NamedStore.PRIMARY_COLOR, newColor.name) }
    }
}