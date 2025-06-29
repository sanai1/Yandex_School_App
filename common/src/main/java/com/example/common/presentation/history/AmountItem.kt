package com.example.common.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.ListItem
import com.example.common.presentation.TypeListItem

@Composable
fun AmountItem(amount: String, modifier: Modifier) {
    ListItem(
        itemModelUI = ListItemModelUI(
            picture = null,
            title = "Сумма",
            description = null,
            info = amount,
            typeListItem = TypeListItem.USUAL
        ),
        modifier = modifier
    )
}