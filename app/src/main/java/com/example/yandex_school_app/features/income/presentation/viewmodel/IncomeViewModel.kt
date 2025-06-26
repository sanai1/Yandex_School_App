package com.example.yandex_school_app.features.income.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.common.domain.entity.TransactionDomain
import com.example.yandex_school_app.common.domain.usecase.TransactionUseCase
import com.example.yandex_school_app.common.presentation.ToastController
import com.example.yandex_school_app.di.AccountManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IncomeViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val accountManager: AccountManager
) : ViewModel() {
    private val _incomeToday = MutableStateFlow<List<TransactionDomain>>(emptyList())
    val incomeToday: StateFlow<List<TransactionDomain>> = _incomeToday.asStateFlow()

    fun updateToday() = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            transactionUseCase.getTransactionsByPeriod(
                accountManager.getAccounts().firstOrNull()?.id ?: 209
            )
        }
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> _incomeToday.value =
                response.body!!.filter { it.categoryDomain.isIncome }

            ResponseTemplate.TypeResponse.UNAUTHORIZED -> ToastController.showToast("Ошибка авторизации")
            ResponseTemplate.TypeResponse.ERROR_CLIENT -> ToastController.showToast("Неверный формат дат или ID счета")
            ResponseTemplate.TypeResponse.ERROR_SERVER -> ToastController.showToast("Ошибка сервера")
            else -> ToastController.showToast("Неизвестная ошибка")
        }
    }

    private val _incomeByPeriod = MutableStateFlow<List<TransactionDomain>>(emptyList())
    val incomeByPeriod: StateFlow<List<TransactionDomain>> = _incomeByPeriod.asStateFlow()

    fun updateByPeriod(startDate: String, endDate: String) = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            transactionUseCase.getTransactionsByPeriod(
                accountManager.getAccounts().firstOrNull()?.id ?: 209,
                startDate, endDate
            )
        }
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> _incomeByPeriod.value =
                response.body!!.filter { it.categoryDomain.isIncome }
                    .sortedByDescending { it.transactionDate }

            ResponseTemplate.TypeResponse.UNAUTHORIZED -> ToastController.showToast("Ошибка авторизации")
            ResponseTemplate.TypeResponse.ERROR_CLIENT -> ToastController.showToast("Неверный формат дат или ID счета")
            ResponseTemplate.TypeResponse.ERROR_SERVER -> ToastController.showToast("Ошибка сервера")
            else -> ToastController.showToast("Неизвестная ошибка")
        }
    }
}