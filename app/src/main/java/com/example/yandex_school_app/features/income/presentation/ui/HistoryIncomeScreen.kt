package com.example.yandex_school_app.features.income.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yandex_school_app.MainActivity
import com.example.yandex_school_app.common.presentation.ToastController
import com.example.yandex_school_app.common.presentation.history.AmountItem
import com.example.yandex_school_app.common.presentation.history.EndDateItem
import com.example.yandex_school_app.common.presentation.history.ListTransaction
import com.example.yandex_school_app.common.presentation.history.StartDateItem
import com.example.yandex_school_app.features.income.presentation.viewmodel.IncomeViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun HistoryIncomeScreen(
    modifier: Modifier = Modifier, viewModel: IncomeViewModel = viewModel(
        factory = (LocalContext.current as MainActivity).viewModelFactory
    )
) {
    Column {
        val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val startDate = remember {
            mutableStateOf(LocalDate.now().withDayOfMonth(1))
        }
        val endDate = remember { mutableStateOf(LocalDate.now()) }
        fun updateList() {
            viewModel.updateByPeriod(
                startDate.value.toString().split("-").reversed().joinToString("."),
                if (endDate.value == LocalDate.now()) dateFormatter.format(Date()) else endDate.value.toString()
                    .split("-").reversed().joinToString(".")
            )
        }
        StartDateItem(
            startDate.value,
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        ) { newStartDate ->
            val newDate = LocalDate.parse(newStartDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            if (newDate.isBefore(endDate.value) || newDate == endDate.value) {
                startDate.value = newDate
                updateList()
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
                endDate.value = newDate
                updateList()
            } else {
                ToastController.showToast("Конец периода должен быть после начала")
            }
        }
        val transactions = viewModel.incomeByPeriod.collectAsState()
        updateList()
        AmountItem(
            amount = "${
                transactions.value.sumOf {
                    it.amount.replace("[^0-9]".toRegex(), "").toLongOrNull() ?: 0
                }.toString().reversed().chunked(3).joinToString(" ").reversed()
            } ₽",
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        )
        ListTransaction(transactions.value, modifier)
    }
}