package com.example.common.store

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object AppTheme {
    enum class PrimaryColorVariant(
        val title: String
    ) {
        GREEN("Зеленый"), BLUE("Синий"), PURPLE("Фиолетовый"), ORANGE("Оранжевый")
    }

    fun PrimaryColorVariant.toColor(): Color = when (this) {
        PrimaryColorVariant.GREEN -> Color(0xFF2AE881)
        PrimaryColorVariant.BLUE -> Color(0xFF2196F3)
        PrimaryColorVariant.PURPLE -> Color(0xFF9C27B0)
        PrimaryColorVariant.ORANGE -> Color(0xFFFF9800)
    }

    fun colorScheme(isDarkTheme: Boolean, primaryColorVariant: PrimaryColorVariant): ColorScheme =
        when (primaryColorVariant) {
            PrimaryColorVariant.GREEN -> if (isDarkTheme) greenDarkScheme else greenLightScheme
            PrimaryColorVariant.BLUE -> if (isDarkTheme) blueDarkScheme else blueLightScheme
            PrimaryColorVariant.PURPLE -> if (isDarkTheme) purpleDarkScheme else purpleLightScheme
            PrimaryColorVariant.ORANGE -> if (isDarkTheme) orangeDarkScheme else orangeLightScheme
        }

    private val greenLightScheme = lightColorScheme(
        primary = Color(0xFF2AE881),
        onPrimary = Color(0xFF1D1B20),
        secondary = Color(0xFFD4FAE6),
        onSecondary = Color(0xFF1D1B20),
        tertiary = Color(0xFFECE6F0),
        onTertiary = Color(0xFF49454F),
        surface = Color(0xFFE46962),
        onSurface = Color.Companion.White,
        background = Color(0xFFFEF7FF),
        onBackground = Color(0xFF1D1B20),
    )

    private val greenDarkScheme = darkColorScheme(
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

    private val blueLightScheme = lightColorScheme(
        primary = Color(0xFF4285F4), // Яркий синий (Google Blue)
        onPrimary = Color(0xFFFFFFFF), // Белый текст для лучшего контраста
        secondary = Color(0xFFD6E3FF), // Светло-голубой
        onSecondary = Color(0xFF1D1B20),
        tertiary = Color(0xFFECE6F0),
        onTertiary = Color(0xFF49454F),
        surface = Color(0xFFE46962), // Красный остается как в оригинале
        onSurface = Color.Companion.White,
        background = Color(0xFFFEF7FF),
        onBackground = Color(0xFF1D1B20)
    )

    private val blueDarkScheme = darkColorScheme(
        primary = Color(0xFF8AB4F8), // Более светлый синий для темной темы
        onPrimary = Color(0xFF000000),
        secondary = Color(0xFF1A305C), // Темно-синий
        onSecondary = Color(0xFFE6E0E9),
        tertiary = Color(0xFF36343B),
        onTertiary = Color(0xFFCAC4D0),
        surface = Color(0xFFC54A42), // Красный как в оригинале
        onSurface = Color(0xFFE6E0E9),
        background = Color(0xFF141218),
        onBackground = Color(0xFFE6E0E9)
    )

    private val purpleLightScheme = lightColorScheme(
        primary = Color(0xFF9C27B0), // Яркий фиолетовый
        onPrimary = Color(0xFFFFFFFF),
        secondary = Color(0xFFF0D6F5), // Светло-фиолетовый
        onSecondary = Color(0xFF1D1B20),
        tertiary = Color(0xFFECE6F0),
        onTertiary = Color(0xFF49454F),
        surface = Color(0xFFE46962), // Красный
        onSurface = Color.Companion.White,
        background = Color(0xFFFEF7FF),
        onBackground = Color(0xFF1D1B20)
    )

    private val purpleDarkScheme = darkColorScheme(
        primary = Color(0xFFBA68C8), // Светлый фиолетовый
        onPrimary = Color(0xFF000000),
        secondary = Color(0xFF3E2A45), // Темно-фиолетовый
        onSecondary = Color(0xFFE6E0E9),
        tertiary = Color(0xFF36343B),
        onTertiary = Color(0xFFCAC4D0),
        surface = Color(0xFFC54A42), // Красный
        onSurface = Color(0xFFE6E0E9),
        background = Color(0xFF141218),
        onBackground = Color(0xFFE6E0E9)
    )

    private val orangeLightScheme = lightColorScheme(
        primary = Color(0xFFFF9800), // Яркий оранжевый
        onPrimary = Color(0xFF000000), // Черный текст для лучшей читаемости
        secondary = Color(0xFFFFE0B2), // Светло-оранжевый
        onSecondary = Color(0xFF1D1B20),
        tertiary = Color(0xFFECE6F0),
        onTertiary = Color(0xFF49454F),
        surface = Color(0xFFE46962), // Красный
        onSurface = Color.Companion.White,
        background = Color(0xFFFEF7FF),
        onBackground = Color(0xFF1D1B20)
    )

    private val orangeDarkScheme = darkColorScheme(
        primary = Color(0xFFFFB74D), // Светлый оранжевый
        onPrimary = Color(0xFF000000),
        secondary = Color(0xFF543D1A), // Темно-оранжевый
        onSecondary = Color(0xFFE6E0E9),
        tertiary = Color(0xFF36343B),
        onTertiary = Color(0xFFCAC4D0),
        surface = Color(0xFFC54A42), // Красный
        onSurface = Color(0xFFE6E0E9),
        background = Color(0xFF141218),
        onBackground = Color(0xFFE6E0E9)
    )
}