package com.example.expense.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.domain.entity.TransactionDomain
import com.example.common.presentation.base_visible.ErrorVisible
import com.example.common.presentation.base_visible.LoadingVisible
import com.example.common.presentation.base_visible.VisibleData
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem
import com.example.expense.presentation.ExpenseViewModel

@Composable
fun ExpenseScreen(
    modifier: Modifier,
    viewModel: ExpenseViewModel
) {
    val transactions = viewModel.expensesToday.collectAsStateWithLifecycle()
    viewModel.updateToday()
    when (transactions.value) {
        is VisibleData.Loading -> LoadingVisible()
        is VisibleData.Success -> Column {
            ListItem(
                itemModelUI = ListItemModelUI(
                    picture = null,
                    title = "Всего",
                    description = null,
                    info = "${
                        (transactions.value as VisibleData.Success<List<TransactionDomain>>).data.sumOf {
                            it.amount.replace("[^0-9]".toRegex(), "").toLongOrNull() ?: 0
                        }.toString().reversed().chunked(3).joinToString(" ").reversed()
                    } ${viewModel.getSelectedAccount().value.currency.symbol}",
                    typeListItem = TypeListItem.USUAL
                ),
                modifier = modifier
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.surface)
            )
            (transactions.value as VisibleData.Success<List<TransactionDomain>>).data.forEach {
                ListItem(
                    itemModelUI = ListItemModelUI(
                        picture = it.categoryDomain.emoji,
                        title = it.categoryDomain.name,
                        description = it.comment,
                        info = "${it.amount} ${viewModel.getSelectedAccount().value.currency.symbol}",
                        typeListItem = TypeListItem.ARROW
                    ),
                    modifier = modifier.height(70.dp)
                )
            }
        }

        is VisibleData.Error -> (transactions.value as VisibleData.Error).let {
            ErrorVisible(it.type)
        }
    }
}