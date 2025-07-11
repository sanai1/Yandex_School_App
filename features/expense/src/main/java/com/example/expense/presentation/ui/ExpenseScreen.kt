package com.example.expense.presentation.ui

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.presentation.base_visible.ErrorVisible
import com.example.common.presentation.base_visible.LoadingVisible
import com.example.common.presentation.base_visible.VisibleData
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem
import com.example.expense.presentation.ExpenseViewModel

@Composable
fun ExpenseScreen(
    modifier: Modifier,
    onClickDetailsTransaction: () -> Unit,
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
                        DecimalFormat("#.00").format((transactions.value as VisibleData.Success<List<TransactionDomain>>).data.sumOf {
                            it.amount.toDoubleOrNull() ?: 0.0
                        }).toString().reversed().let {
                            it.substring(0, 3) + it.substring(3).chunked(3).joinToString(" ")
                                .ifEmpty { "0" }
                        }.reversed()
                    } ${viewModel.getSelectedAccount().value.currency.symbol}",
                    typeListItem = TypeListItem.USUAL
                ),
                modifier = modifier
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.secondary)
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
                    modifier = modifier.height(70.dp),
                    onClickDetails = {
                        viewModel.setSelectedTransaction(it)
                        onClickDetailsTransaction.invoke()
                    }
                )
            }
        }

        is VisibleData.Error -> (transactions.value as VisibleData.Error).let {
            ErrorVisible(it.type)
        }
    }
}