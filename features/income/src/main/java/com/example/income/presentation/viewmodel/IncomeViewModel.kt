package com.example.income.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.ResponseTemplate
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.usecase.TransactionUseCase
import com.example.common.store.AccountStore
import com.example.common.presentation.base_visible.VisibleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class IncomeViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
) : ViewModel() {
    private val _incomeToday =
        MutableStateFlow<VisibleData<List<TransactionDomain>>>(VisibleData.Loading())
    val incomeToday: StateFlow<VisibleData<List<TransactionDomain>>> = _incomeToday.asStateFlow()

    fun updateToday() = viewModelScope.launch(Dispatchers.IO) {
        val response = transactionUseCase.getTransactionsByPeriod(
            AccountStore.selectedAccount.value.id
        )
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> response.body?.let { it ->
                _incomeToday.value = VisibleData.Success(it.filter { it.categoryDomain.isIncome })
            }

            ResponseTemplate.TypeResponse.ERROR_CLIENT -> _incomeToday.value =
                VisibleData.Error(response.typeResponse, "Неверный формат дат или ID счета")

            else -> _incomeToday.value = VisibleData.Error(response.typeResponse)
        }
    }

    fun getSelectedAccount() = AccountStore.selectedAccount

    private val _incomeByPeriod =
        MutableStateFlow<VisibleData<List<TransactionDomain>>>(VisibleData.Loading())
    val incomeByPeriod: StateFlow<VisibleData<List<TransactionDomain>>> =
        _incomeByPeriod.asStateFlow()

    fun updateByPeriod(startDate: String, endDate: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = transactionUseCase.getTransactionsByPeriod(
            AccountStore.selectedAccount.value.id,
            startDate, endDate
        )
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> response.body?.let { it ->
                _incomeByPeriod.value = VisibleData.Success(it.filter { it.categoryDomain.isIncome }
                    .sortedByDescending { it.transactionDate })
            }

            ResponseTemplate.TypeResponse.ERROR_CLIENT -> _incomeByPeriod.value =
                VisibleData.Error(response.typeResponse, "Неверный формат дат или ID счета")

            else -> _incomeByPeriod.value = VisibleData.Error(response.typeResponse)
        }
    }
}