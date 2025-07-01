package com.example.common.presentation.history

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem

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
        modifier = modifier.height(56.dp)
    )
}