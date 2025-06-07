package com.example.yandex_school_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yandex_school_app.ui.common.ListItem

@Composable
fun ExpenseScreen(modifier: Modifier) {
    val titleList = listOf(
        "Аренда квартиры",
        "Одежда",
        "На собачку",
        "На собачку",
        "Ремонт квартиры",
        "Продукты",
        "Спортзал",
        "Медицина"
    )
    var fl = false
    Column {
        titleList.forEach {
            ListItem(
                picture = Icons.Default.Favorite,
                title = it,
                description = if (it == "На собачку") {
                    if (fl) "Энни" else {
                        fl = true
                        "Джек"
                    }
                } else null,
                info = "100 000 ₽",
                modifier = modifier
            ) {
                Text("Конец")
            }
        }
    }
}