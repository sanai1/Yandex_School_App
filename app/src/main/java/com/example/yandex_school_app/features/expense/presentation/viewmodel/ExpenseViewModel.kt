package com.example.yandex_school_app.features.expense.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.common.domain.entity.TransactionDomain
import com.example.yandex_school_app.common.domain.usecase.TransactionUseCase
import com.example.yandex_school_app.di.AccountManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExpenseViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val accountManager: AccountManager
) : ViewModel() {
    private val _expensesToday = MutableStateFlow<List<TransactionDomain>>(emptyList())
    val expensesToday: StateFlow<List<TransactionDomain>> = _expensesToday.asStateFlow()

    fun updateToday() = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            transactionUseCase.getTransactionsByPeriod(
                accountManager.getAccounts().firstOrNull()?.id ?: 209
            )
        }
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> _expensesToday.value =
                response.body!!.filter { it.categoryDomain.isIncome.not() }

            else -> {}
        }
    }

    private val _expensesByPeriod = MutableStateFlow<List<TransactionDomain>>(emptyList())
    val expensesByPeriod: StateFlow<List<TransactionDomain>> = _expensesByPeriod.asStateFlow()

    fun updateByPeriod(startDate: String, endDate: String) = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            transactionUseCase.getTransactionsByPeriod(
                accountManager.getAccounts().firstOrNull()?.id ?: 209,
                startDate, endDate
            )
        }
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> _expensesByPeriod.value =
                response.body!!.filter { it.categoryDomain.isIncome.not() }
                    .sortedByDescending { it.transactionDate }

            else -> {}
        }
    }
}