package com.example.yandex_school_app.features.expense.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.data.network.ResponseTemplate
import com.example.common.domain.entity.TransactionDomain
import com.example.common.domain.usecase.TransactionUseCase
import com.example.common.manager.AccountManager
import com.example.common.presentation.base_visible.VisibleData
import com.example.yandex_school_app.Mok
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
    private val _expensesToday =
        MutableStateFlow<VisibleData<List<TransactionDomain>>>(VisibleData.Loading())
    val expensesToday: StateFlow<VisibleData<List<TransactionDomain>>> =
        _expensesToday.asStateFlow()

    fun updateToday() = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            transactionUseCase.getTransactionsByPeriod(
                accountManager.getAccounts().firstOrNull()?.id ?: 209
            ).copy(body = Mok.transactionExpense) // TODO: убрать моковые данные
        }
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> _expensesToday.value =
                VisibleData.Success(response.body!!.filter { it.categoryDomain.isIncome.not() })

            ResponseTemplate.TypeResponse.ERROR_CLIENT -> _expensesToday.value =
                VisibleData.Error(response.typeResponse, "Неверный формат дат или ID счета")

            else -> _expensesToday.value = VisibleData.Error(response.typeResponse)
        }
    }

    private val _expensesByPeriod =
        MutableStateFlow<VisibleData<List<TransactionDomain>>>(VisibleData.Loading())
    val expensesByPeriod: StateFlow<VisibleData<List<TransactionDomain>>> =
        _expensesByPeriod.asStateFlow()

    fun updateByPeriod(startDate: String, endDate: String) = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            transactionUseCase.getTransactionsByPeriod(
                accountManager.getAccounts().firstOrNull()?.id ?: 209,
                startDate, endDate
            )
        }
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> _expensesByPeriod.value =
                VisibleData.Success(response.body!!.filter { it.categoryDomain.isIncome.not() }
                    .sortedByDescending { it.transactionDate })

            ResponseTemplate.TypeResponse.ERROR_CLIENT -> _expensesByPeriod.value =
                VisibleData.Error(response.typeResponse, "Неверный формат дат или ID счета")

            else -> _expensesByPeriod.value = VisibleData.Error(response.typeResponse)
        }
    }
}