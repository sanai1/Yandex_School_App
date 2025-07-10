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
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeSelection(
    modifier: Modifier,
    time: LocalTime,
    updateTime: (LocalTime) -> Unit
) {
    var time by remember { mutableStateOf(time) }
    ListItem(
        itemModelUI = ListItemModelUI(
            title = "Время",
            info = time.toString().substring(0, 5),
            typeListItem = TypeListItem.USUAL,
        ),
        modifier = modifier,
        onClickTime = {
            time = LocalTime.parse(it, DateTimeFormatter.ofPattern("HH:MM"))
            updateTime.invoke(time)
        }
    )
}