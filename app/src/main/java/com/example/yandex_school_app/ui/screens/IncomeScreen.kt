package com.example.yandex_school_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yandex_school_app.ui.common.ListItem

@Composable
fun IncomeScreen(modifier: Modifier) {

    val titleList = listOf("Зарплата", "Подработка")
    val infoList = listOf("500 000 ₽", "100 000 ₽")
    Column {
        titleList.forEachIndexed { index, item ->
            ListItem(
                picture = null,
                title = item,
                description = null,
                info = infoList[index],
                modifier = modifier
            ) {
                Text("Что-то смешное")
            }
        }
    }
}