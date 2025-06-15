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
import com.example.yandex_school_app.common.presentation.history.AmountItem
import com.example.yandex_school_app.common.presentation.history.EndDateItem
import com.example.yandex_school_app.common.presentation.history.ListTransaction
import com.example.yandex_school_app.common.presentation.history.StartDateItem
import com.example.yandex_school_app.features.income.presentation.viewmodel.IncomeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
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
            mutableStateOf(dateFormatter.format(Calendar.getInstance().let {
                it.set(Calendar.DAY_OF_MONTH, 1)
                it
            }.time))
        }
        val endDate = remember { mutableStateOf("сегодня") }
        fun updateList() {
            viewModel.updateByPeriod(
                startDate.value,
                if (endDate.value == "сегодня") dateFormatter.format(Date()) else endDate.value
            )
        }
        StartDateItem(
            startDate.value,
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        ) { newStartDate ->
            startDate.value = newStartDate
            updateList()
        }
        EndDateItem(
            endDate.value,
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        ) { newEndDate ->
            endDate.value = newEndDate
            updateList()
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