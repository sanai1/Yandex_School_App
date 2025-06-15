package com.example.yandex_school_app.common.presentation.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yandex_school_app.common.domain.entity.ListItemModelUI
import com.example.yandex_school_app.common.domain.entity.TransactionDomain
import com.example.yandex_school_app.common.presentation.ListItem
import com.example.yandex_school_app.common.presentation.TypeListItem

@Composable
fun ListTransaction(transactions: List<TransactionDomain>, modifier: Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        transactions.forEach { item ->
            ListItem(
                itemModelUI = ListItemModelUI(
                    picture = item.categoryDomain.emoji,
                    title = item.categoryDomain.name,
                    description = item.comment,
                    info = item.amount,
                    typeListItem = TypeListItem.ARROW
                ),
                modifier = modifier
            )
        }
    }
}