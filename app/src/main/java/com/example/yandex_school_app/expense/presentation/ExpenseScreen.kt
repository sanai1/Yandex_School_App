package com.example.yandex_school_app.expense.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.yandex_school_app.common.domain.ListItemModelUI
import com.example.yandex_school_app.common.domain.TransactionDomain
import com.example.yandex_school_app.common.presentation.ListItem
import com.example.yandex_school_app.common.presentation.TypeListItem

@Composable
fun ExpenseScreen(listTransactionDomain: List<TransactionDomain>, modifier: Modifier) {
    val transactions = remember { mutableStateOf(listTransactionDomain) }
    Column {
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = null,
                title = "Всего",
                description = null,
                info = "${
                    transactions.value.sumOf {
                        it.amount.replace("[^0-9]".toRegex(), "").toLongOrNull() ?: 0
                    }.toString().reversed().chunked(3).joinToString(" ").reversed()
                } ₽",
                typeListItem = TypeListItem.USUAL
            ),
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        )
        transactions.value.forEach {
            ListItem(
                itemModelUI = ListItemModelUI(
                    picture = it.categoryDomain.emoji,
                    title = it.categoryDomain.name,
                    description = it.comment,
                    info = it.amount,
                    typeListItem = TypeListItem.ARROW
                ),
                modifier = modifier
            )
        }
    }
}