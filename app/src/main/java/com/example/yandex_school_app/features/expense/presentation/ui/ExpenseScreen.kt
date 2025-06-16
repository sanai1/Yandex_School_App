package com.example.yandex_school_app.features.expense.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yandex_school_app.MainActivity
import com.example.yandex_school_app.Mok
import com.example.yandex_school_app.common.domain.entity.ListItemModelUI
import com.example.yandex_school_app.common.presentation.ListItem
import com.example.yandex_school_app.common.presentation.TypeListItem
import com.example.yandex_school_app.features.expense.presentation.viewmodel.ExpenseViewModel

@Composable
fun ExpenseScreen(
    modifier: Modifier,
    viewModel: ExpenseViewModel = viewModel(
        factory = (LocalContext.current as MainActivity).viewModelFactory
    )
) {
    viewModel.updateToday()
    val transactions =
        remember { mutableStateOf(Mok.transactionExpense) } // TODO: убрать моковые данные
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