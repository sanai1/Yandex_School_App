package com.example.yandex_school_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.common.store.AppTheme

@Composable
fun Yandex_School_AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    primaryColorVariant: AppTheme.PrimaryColorVariant,
    content: @Composable () -> Unit
) {
    val colorScheme = AppTheme.colorScheme(darkTheme, primaryColorVariant)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}