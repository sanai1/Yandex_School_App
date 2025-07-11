package com.example.expense.presentation.ui

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.presentation.base_visible.ErrorVisible
import com.example.common.presentation.base_visible.LoadingVisible
import com.example.common.presentation.base_visible.VisibleData
import com.example.common.presentation.toast.ToastController
import com.example.common.presentation.history.AmountItem
import com.example.common.presentation.history.EndDateItem
import com.example.common.presentation.history.ListTransaction
import com.example.common.presentation.history.StartDateItem
import com.example.expense.presentation.ExpenseViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HistoryExpenseScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpenseViewModel,
    onClickDetailsTransaction: () -> Unit,
) {
    val transactions = viewModel.expensesByPeriod.collectAsStateWithLifecycle()
    val startDate = viewModel.startDate.collectAsStateWithLifecycle()
    val endDate = viewModel.endDate.collectAsStateWithLifecycle()
    viewModel.updateByPeriod()
    when (transactions.value) {
        is VisibleData.Loading -> LoadingVisible()

        is VisibleData.Success -> Column {
            StartDateItem(
                startDate.value,
                modifier = modifier.background(MaterialTheme.colorScheme.surface)
            ) { newStartDate ->
                val newDate =
                    LocalDate.parse(newStartDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                if (newDate.isBefore(endDate.value) || newDate == endDate.value) {
                    viewModel.setStartDate(newDate)
                } else {
                    ToastController.showToast("Начало периода должно быть до его конца")
                }
            }
            EndDateItem(
                endDate.value,
                modifier = modifier.background(MaterialTheme.colorScheme.surface)
            ) { newEndDate ->
                val newDate = LocalDate.parse(newEndDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                if (newDate.isAfter(startDate.value) || newDate == startDate.value) {
                    viewModel.setEndDate(newDate)
                } else {
                    ToastController.showToast("Конец периода должен быть после начала")
                }
            }
            AmountItem(
                amount = "${
                    DecimalFormat("#.00").format((transactions.value as VisibleData.Success<List<TransactionDomain>>).data.sumOf {
                        it.amount.toDoubleOrNull() ?: 0.0
                    }).toString().reversed().let {
                        it.substring(0, 3) + it.substring(3).chunked(3).joinToString(" ")
                            .ifEmpty { "0" }
                    }.reversed()
                } ${viewModel.getSelectedAccount().value.currency.symbol}",
                modifier = modifier.background(MaterialTheme.colorScheme.surface)
            )
            ListTransaction(
                (transactions.value as VisibleData.Success<List<TransactionDomain>>).data,
                viewModel.getSelectedAccount().value.currency,
                modifier,
                onClickDetails = { transaction ->
                    viewModel.setSelectedTransaction(transaction)
                    onClickDetailsTransaction.invoke()
                }
            )
        }

        is VisibleData.Error -> (transactions.value as VisibleData.Error<List<TransactionDomain>>).let {
            ErrorVisible(it.type, it.message)
        }
    }
}