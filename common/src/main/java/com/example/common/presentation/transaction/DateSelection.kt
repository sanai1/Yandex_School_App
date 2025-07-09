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

@Composable
fun DateSelection(
    modifier: Modifier,
    updateDate: (String) -> Unit
) {
    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    ListItem(
        itemModelUI = ListItemModelUI(
            title = "Дата",
            info = date,
            typeListItem = TypeListItem.USUAL
        ),
        modifier = modifier,
        onClickDate = {
            date = it
            updateDate.invoke(it)
        }
    )
}