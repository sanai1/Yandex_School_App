package com.example.common.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateSelection(
    modifier: Modifier,
    date: LocalDate,
    updateDate: (LocalDate) -> Unit
) {
    var date by remember { mutableStateOf(date) }
    ListItem(
        itemModelUI = ListItemModelUI(
            title = "Дата",
            info = if (date == LocalDate.now()) "сегодня" else date.toString().split("-").reversed()
                .joinToString("."),
            typeListItem = TypeListItem.USUAL
        ),
        modifier = modifier,
        onClickDate = {
            date = LocalDate.parse(it, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            updateDate.invoke(date)
        }
    )
}