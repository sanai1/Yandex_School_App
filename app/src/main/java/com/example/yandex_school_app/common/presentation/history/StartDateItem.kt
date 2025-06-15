package com.example.yandex_school_app.common.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yandex_school_app.common.domain.entity.ListItemModelUI
import com.example.yandex_school_app.common.presentation.ListItem
import com.example.yandex_school_app.common.presentation.TypeListItem

@Composable
fun StartDateItem(startDate: String, modifier: Modifier, updateDate: (String) -> Unit) {
    ListItem(
        itemModelUI = ListItemModelUI(
            title = "Начало",
            info = startDate,
            typeListItem = TypeListItem.USUAL,
        ),
        modifier = modifier,
        onClickDate = { newStartDate ->
            updateDate.invoke(newStartDate)
        }
    )
}