package com.example.yandex_school_app.features.income.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.yandex_school_app.common.domain.entity.ListItemModelUI
import com.example.yandex_school_app.common.domain.entity.TransactionDomain
import com.example.yandex_school_app.common.presentation.ListItem
import com.example.yandex_school_app.common.presentation.TypeListItem

@Composable
fun IncomeScreen(listTransactionDomain: List<TransactionDomain>, modifier: Modifier) {
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
        transactions.value.forEachIndexed { index, item ->
            ListItem(
                itemModelUI = ListItemModelUI(
                    picture = item.categoryDomain.emoji,
                    title = item.categoryDomain.name,
                    description = null,
                    info = item.amount,
                    typeListItem = TypeListItem.ARROW
                ),
                modifier = modifier
            )
        }
    }
}