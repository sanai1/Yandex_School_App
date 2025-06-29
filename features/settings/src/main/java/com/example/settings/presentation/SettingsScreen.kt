package com.example.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem


@Composable
fun SettingsScreen(modifier: Modifier) {
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
                modifier = modifier,
            )
        }
    }
}