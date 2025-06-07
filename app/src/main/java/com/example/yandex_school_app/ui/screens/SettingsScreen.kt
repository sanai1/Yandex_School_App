package com.example.yandex_school_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yandex_school_app.ui.common.ListItem


@Composable
fun SettingsScreen(modifier: Modifier) {
    val listTitles = listOf(
        "Светлая темная авто",
        "Основной цвет",
        "звуки",
        "Хаптики",
        "Код пароль",
        "Синхронизация",
        "Язык",
        "О программе"
    )
    Column {
        listTitles.forEach {
            ListItem(
                picture = null,
                title = it,
                description = null,
                info = null,
                modifier = modifier,
            ) {
                if (it == "Светлая темная авто") Text("Переключалка")
                else Text("click")
            }
        }
    }
}