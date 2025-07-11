package com.example.income.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.entity.category.CategoryDomain
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.common.domain.usecase.AccountUseCase
import com.example.common.domain.usecase.CategoryUseCase
import com.example.common.domain.usecase.TransactionUseCase
import com.example.common.presentation.base_visible.VisibleData
import com.example.common.presentation.toast.ToastController
import com.example.common.store.AccountStore
import com.example.common.store.TransactionStore
import com.example.network.ResponseTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class IncomeViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val categoryUseCase: CategoryUseCase,
    private val accountUseCase: AccountUseCase,
    private val transactionStore: TransactionStore
) : ViewModel() {
    private val _incomeToday =
        MutableStateFlow<VisibleData<List<TransactionDomain>>>(VisibleData.Loading())
    val incomeToday: StateFlow<VisibleData<List<TransactionDomain>>> = _incomeToday.asStateFlow()

    fun updateToday() = viewModelScope.launch(Dispatchers.IO) {
        val response = accountUseCase.getAllCashAccount()
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> {
                val list = mutableListOf<TransactionDomain>()
                response.body?.forEach { account ->
                    transactionUseCase.getTransactionsByPeriod(account.id).let {
                        if (it.typeResponse == ResponseTemplate.TypeResponse.SUCCESS) {
                            it.body?.forEach { transaction -> list.add(transaction) }
                        }
                    }
                }
                _incomeToday.value = VisibleData.Success(list.filter { it.categoryDomain.isIncome })
            }

            else -> _incomeToday.value = VisibleData.Error(response.typeResponse)
        }
    }

    fun getSelectedAccount() = AccountStore.Example.selectedAccount

    fun setSelectedTransaction(selectionTransactionDomain: TransactionDomain) {
        transactionStore.setSelectedTransaction(selectionTransactionDomain)
    }

    private val _incomeByPeriod =
        MutableStateFlow<VisibleData<List<TransactionDomain>>>(VisibleData.Loading())
    private val _startDate = MutableStateFlow(LocalDate.now().withDayOfMonth(1))
    private val _endDate = MutableStateFlow(LocalDate.now())
    val incomeByPeriod: StateFlow<VisibleData<List<TransactionDomain>>> =
        _incomeByPeriod.asStateFlow()
    val startDate: StateFlow<LocalDate> = _startDate.asStateFlow()
    val endDate: StateFlow<LocalDate> = _endDate.asStateFlow()

    fun setStartDate(newStartDate: LocalDate) {
        _startDate.value = newStartDate
    }

    fun setEndDate(newEndDate: LocalDate) {
        _endDate.value = newEndDate
    }

    fun updateByPeriod() = viewModelScope.launch(Dispatchers.IO) {
        val response = accountUseCase.getAllCashAccount()
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> {
                val list = mutableListOf<TransactionDomain>()
                response.body?.forEach { account ->
                    transactionUseCase.getTransactionsByPeriod(
                        account.id,
                        startDate.value.toString(), endDate.value.toString()
                    ).let {
                        if (it.typeResponse == ResponseTemplate.TypeResponse.SUCCESS) {
                            it.body?.forEach { transaction -> list.add(transaction) }
                        }
                    }
                }
                _incomeByPeriod.value =
                    VisibleData.Success(list.filter { it.categoryDomain.isIncome })
            }

            else -> _incomeByPeriod.value = VisibleData.Error(response.typeResponse)
        }
    }

    fun createTransaction(transactionPartDomain: TransactionPartDomain) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        val response = transactionUseCase.createTransaction(transactionPartDomain)
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> updateToday()
            else -> ToastController.showToast(response.typeResponse.text)
        }
    }

    fun updateTransaction(transactionId: Int, transactionPartDomain: TransactionPartDomain) =
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val response = transactionUseCase.updateTransactionById(
                transactionId = transactionId,
                transaction = transactionPartDomain
            )
            when (response.typeResponse) {
                ResponseTemplate.TypeResponse.SUCCESS -> updateToday()
                else -> ToastController.showToast(response.typeResponse.text)
            }
        }

    fun deleteTransactionById(transactionId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = transactionUseCase.deleteTransactionById(transactionId)
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> updateToday()
            else -> ToastController.showToast(response.typeResponse.text)
        }
    }

    private val _categoryIncome =
        MutableStateFlow<VisibleData<List<CategoryDomain>>>(VisibleData.Loading())
    val categoryIncome: StateFlow<VisibleData<List<CategoryDomain>>> = _categoryIncome.asStateFlow()

    fun updateCategoryIncome() = viewModelScope.launch(Dispatchers.IO) {
        val response = categoryUseCase.getIncomeCategories()
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> response.body?.let {
                _categoryIncome.value = VisibleData.Success(it)
            }

            else -> _categoryIncome.value = VisibleData.Error(response.typeResponse)
        }
    }

    private val _accountList =
        MutableStateFlow<VisibleData<List<AccountDomain>>>(VisibleData.Loading())
    val accountList: StateFlow<VisibleData<List<AccountDomain>>> = _accountList.asStateFlow()

    fun updateAccounts() = viewModelScope.launch(Dispatchers.IO) {
        val response = accountUseCase.getAllCashAccount()
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> response.body?.let {
                _accountList.value = VisibleData.Success(it)
            }

            else -> _accountList.value = VisibleData.Error(response.typeResponse)
        }
    }
}