package com.example.yandex_school_app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2AE881),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF1E3A2A),
    onSecondary = Color(0xFFE6E0E9),
    tertiary = Color(0xFF36343B),
    onTertiary = Color(0xFFCAC4D0),
    surface = Color(0xFFC54A42),
    onSurface = Color(0xFFE6E0E9),
    background = Color(0xFF141218),
    onBackground = Color(0xFFE6E0E9)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2AE881),
    onPrimary = Color(0xFF1D1B20),
    secondary = Color(0xFFD4FAE6),
    onSecondary = Color(0xFF1D1B20),
    tertiary = Color(0xFFECE6F0),
    onTertiary = Color(0xFF49454F),
    surface = Color(0xFFE46962),
    onSurface = Color.White,
    background = Color(0xFFFEF7FF),
    onBackground = Color(0xFF1D1B20),
)

@Composable
fun Yandex_School_AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}