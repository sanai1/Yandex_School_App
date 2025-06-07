package com.example.yandex_school_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yandex_school_app.ui.common.ListItem

@Composable
fun ArticlesScreen(modifier: Modifier) {
    val listTitles = listOf(
        "Аренда квартиры",
        "Одежда",
        "На собачку",
        "На собачку",
        "Ремонт квартиры",
        "Продукты",
        "Спортзал",
        "Медицина"
    )
    Column {
        listTitles.forEachIndexed { index, item ->
            ListItem(
                picture = Icons.Default.Star,
                title = item,
                description = null,
                info = null,
                modifier = modifier,
                click = null
            )
        }
    }
}