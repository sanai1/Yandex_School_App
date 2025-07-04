package com.example.expense.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.domain.entity.TransactionDomain
import com.example.common.domain.usecase.TransactionUseCase
import com.example.common.presentation.base_visible.VisibleData
import com.example.common.store.AccountStore
import com.example.network.ResponseTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExpenseViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val accountManager: AccountStore
) : ViewModel() {
    private val _expensesToday =
        MutableStateFlow<VisibleData<List<TransactionDomain>>>(VisibleData.Loading())
    val expensesToday: StateFlow<VisibleData<List<TransactionDomain>>> =
        _expensesToday.asStateFlow()

    fun updateToday() = viewModelScope.launch(Dispatchers.IO) {
        val response = transactionUseCase.getTransactionsByPeriod(
            accountManager.selectedAccount.value.id
        )
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> response.body?.let { it ->
                _expensesToday.value =
                    VisibleData.Success(it.filter { it.categoryDomain.isIncome.not() })

            }

            ResponseTemplate.TypeResponse.ERROR_CLIENT -> _expensesToday.value =
                VisibleData.Error(response.typeResponse, "Неверный формат дат или ID счета")

            else -> _expensesToday.value = VisibleData.Error(response.typeResponse)
        }
    }

    fun getSelectedAccount() = accountManager.selectedAccount

    private val _expensesByPeriod =
        MutableStateFlow<VisibleData<List<TransactionDomain>>>(VisibleData.Loading())
    val expensesByPeriod: StateFlow<VisibleData<List<TransactionDomain>>> =
        _expensesByPeriod.asStateFlow()

    fun updateByPeriod(startDate: String, endDate: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = transactionUseCase.getTransactionsByPeriod(
            accountManager.selectedAccount.value.id,
            startDate, endDate
        )
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> response.body?.let { it ->
                _expensesByPeriod.value =
                    VisibleData.Success(it.filter { it.categoryDomain.isIncome.not() }
                        .sortedByDescending { it.transactionDate })

            }

            ResponseTemplate.TypeResponse.ERROR_CLIENT -> _expensesByPeriod.value =
                VisibleData.Error(response.typeResponse, "Неверный формат дат или ID счета")

            else -> _expensesByPeriod.value = VisibleData.Error(response.typeResponse)
        }
    }
}