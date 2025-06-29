package com.example.common.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.ListItem
import com.example.common.presentation.TypeListItem
import java.time.LocalDate

@Composable
fun EndDateItem(endDate: LocalDate, modifier: Modifier, updateDate: (String) -> Unit) {
    ListItem(
        itemModelUI = ListItemModelUI(
            title = "Конец",
            info = if (endDate == LocalDate.now()) "сегодня" else endDate.toString().split("-")
                .reversed().joinToString("."),
            typeListItem = TypeListItem.USUAL
        ),
        modifier = modifier,
        onClickDate = { newEndDate ->
            updateDate.invoke(newEndDate)
        }
    )
}