package com.example.expense.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.presentation.analytics.AmountAnalyticsItem
import com.example.common.presentation.analytics.EndDateAnalyticsItem
import com.example.common.presentation.analytics.ListAnalyticsTransaction
import com.example.common.presentation.analytics.StartDateAnalyticsItem
import com.example.common.presentation.base_visible.ErrorVisible
import com.example.common.presentation.base_visible.LoadingVisible
import com.example.common.presentation.base_visible.VisibleData
import com.example.common.presentation.toast.ToastController
import com.example.expense.presentation.ExpenseViewModel
import com.example.pie.PieChart
import com.example.pie.PieChartData
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun AnalyticsExpenseScreen(
    modifier: Modifier,
    viewModel: ExpenseViewModel,
    onClickDetailsTransaction: () -> Unit
) {
    viewModel.updateAnalytics()
    val transaction by viewModel.expenseAnalytics.collectAsStateWithLifecycle()
    val startDate by viewModel.startDateAnalytics.collectAsStateWithLifecycle()
    val endDate by viewModel.endDateAnalytics.collectAsStateWithLifecycle()
    when (transaction) {
        is VisibleData.Loading -> LoadingVisible()
        is VisibleData.Success -> Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            StartDateAnalyticsItem(
                startDate = startDate,
                modifier = modifier.background(MaterialTheme.colorScheme.background)
            ) { newStartDate ->
                val newDate =
                    LocalDate.parse(newStartDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                if (newDate.isBefore(endDate) || newDate == endDate) {
                    viewModel.setStartDateAnalytics(newDate)
                } else {
                    ToastController.showToast("Начало периода должно быть до его конца")
                }
            }
            EndDateAnalyticsItem(
                endDate = endDate,
                modifier = modifier.background(MaterialTheme.colorScheme.background)
            ) { newEndDate ->
                val newDate = LocalDate.parse(newEndDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                if (newDate.isAfter(startDate) || newDate == startDate) {
                    viewModel.setEndDateAnalytics(newDate)
                } else {
                    ToastController.showToast("Конец периода должен быть после начала")
                }
            }
            val amount = (transaction as VisibleData.Success).data.sumOf {
                it.amount.toDoubleOrNull() ?: 0.0
            }
            AmountAnalyticsItem(
                amount = "${
                    DecimalFormat("#.00").format(amount).toString().reversed().let {
                        it.substring(0, 3) + it.substring(3).chunked(3).joinToString(" ")
                            .ifEmpty { "0" }
                    }.reversed()
                } ${viewModel.getSelectedAccount().value.currency.symbol}",
                modifier = modifier.background(MaterialTheme.colorScheme.background)
            )
            PieChart(
                data = (transaction as VisibleData.Success<List<TransactionDomain>>).data.groupBy { it.categoryDomain.id }
                    .map { (_, value) ->
                        PieChartData(
                            value = value.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toFloat(),
                            name = value[0].categoryDomain.name
                        )
                    },
                colors = listOf(
                    Color(0xFFA2D2FF),
                    Color(0xFFBDE0FE),
                    Color(0xFFCDB4DB),
                    Color(0xFFFFC8DD),
                    Color(0xFFFFAFCC),
                    Color(0xFFB5EAD7),
                    Color(0xFFC7F9CC),
                    Color(0xFFE2F0CB),
                    Color(0xFFFFDAC1),
                    Color(0xFFFFF3B0)
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            ) {}
            ListAnalyticsTransaction(
                transactions = (transaction as VisibleData.Success<List<TransactionDomain>>).data,
                currency = viewModel.getSelectedAccount().value.currency,
                modifier = modifier,
                getPartForTransaction = { it ->
                    val transactionAmount = it.toDoubleOrNull() ?: 1.0
                    return@ListAnalyticsTransaction DecimalFormat("#.00").format(amount.let {
                        if (it == 0.0) 0.0 else (transactionAmount / amount) * 100
                    }).toString().reversed().let {
                        it.substring(0, 3) + it.substring(3).ifEmpty { "0" }
                    }.reversed()
                }
            ) { transaction ->
                viewModel.setSelectedTransaction(transaction)
                onClickDetailsTransaction.invoke()
            }
        }

        is VisibleData.Error -> (transaction as VisibleData.Error).let {
            ErrorVisible(it.type, it.message)
        }
    }
}