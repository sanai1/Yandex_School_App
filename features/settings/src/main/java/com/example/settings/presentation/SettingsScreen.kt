package com.example.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem


@Composable
fun SettingsScreen(
    modifier: Modifier,
    viewmodel: SettingsViewmodel,
    onChangeTheme: (Boolean) -> Unit
) {
    val listTitles = listOf(
        "Тёмная тема",
        "Основной цвет",
        "Звуки",
        "Хаптики",
        "Код пароль",
        "Синхронизация",
        "Язык",
        "О программе"
    )
    val isDarkTheme by viewmodel.isDarkTheme.collectAsStateWithLifecycle()
    Column {
        listTitles.forEach {
            ListItem(
                itemModelUI = ListItemModelUI(
                    picture = null,
                    title = it,
                    description = null,
                    info = null,
                    typeListItem = if (it == "Тёмная тема") TypeListItem.SWITCH else TypeListItem.ARROW
                ),
                modifier = modifier.height(56.dp),
                onClickChangeTheme = { isDark ->
                    viewmodel.setTheme(isDark)
                    onChangeTheme.invoke(isDark)
                },
                isDarkTheme = isDarkTheme
            )
        }
    }
}