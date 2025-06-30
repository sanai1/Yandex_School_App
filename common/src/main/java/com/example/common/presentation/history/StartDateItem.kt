package com.example.common.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem
import java.time.LocalDate

@Composable
fun StartDateItem(startDate: LocalDate, modifier: Modifier, updateDate: (String) -> Unit) {
    ListItem(
        itemModelUI = ListItemModelUI(
            title = "Начало",
            info = startDate.toString().split("-").reversed().joinToString("."),
            typeListItem = TypeListItem.USUAL,
        ),
        modifier = modifier,
        onClickDate = { newStartDate ->
            updateDate.invoke(newStartDate)
        }
    )
}