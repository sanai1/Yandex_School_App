package com.example.yandex_school_app.features.expense.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yandex_school_app.common.domain.entity.ListItemModelUI
import com.example.yandex_school_app.common.presentation.ListItem
import com.example.yandex_school_app.common.presentation.TypeListItem

@Composable
fun HistoryExpenseScreen(modifier: Modifier = Modifier) {
    Column {
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "Начало",
                description = null,
                info = "Февраль 2025",
                typeListItem = TypeListItem.USUAL
            ),
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        )
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "Конец",
                description = null,
                info = "23:41",
                typeListItem = TypeListItem.USUAL
            ),
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        )
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "Сумма",
                description = null,
                info = "125 686 P",
                typeListItem = TypeListItem.USUAL
            ),
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        )
    }

}