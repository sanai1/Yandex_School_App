package com.example.cash_account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.ResponseTemplate
import com.example.common.presentation.toast.ToastController
import com.example.common.store.AccountStore
import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.usecase.AccountUseCase
import com.example.common.domain.entity.account.Currency
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.usecase.TransactionUseCase
import com.example.common.presentation.base_visible.VisibleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase,
    private val transactionUseCase: TransactionUseCase,
    private val accountManager: AccountStore
) : ViewModel() {
    init {
        updateAllAccount()
    }

    private val _allAccount = MutableStateFlow<List<AccountDomain>>(emptyList())
    private val _transactions =
        MutableStateFlow<VisibleData<List<TransactionDomain>>>(VisibleData.Loading())
    val allAccount: StateFlow<List<AccountDomain>> = _allAccount.asStateFlow()
    val transactions: StateFlow<VisibleData<List<TransactionDomain>>> = _transactions.asStateFlow()

    fun updateAllAccount() = viewModelScope.launch(Dispatchers.IO) {
        val response = accountUseCase.getAllCashAccount()
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> {
                response.body?.let { it ->
                    _allAccount.value = it.sortedBy { it.name }
                }
                if (accountManager.checkAccount()) {
                    (response.body as List<AccountDomain>).firstOrNull()?.let {
                        accountManager.setSelectedAccount(it)
                    }
                }
            }

            ResponseTemplate.TypeResponse.UNAUTHORIZED -> ToastController.showToast("Ошибка авторизации")
            ResponseTemplate.TypeResponse.ERROR_SERVER -> ToastController.showToast("Ошибка сервера")
            else -> ToastController.showToast("Неизвестная ошибка")
        }
    }

    fun getSelectedAccount() = AccountStore.selectedAccount

    fun setSelectedAccountById(newIdAccount: String) {
        allAccount.value.find { it.id.toString() == newIdAccount }?.let {
            accountManager.setSelectedAccount(it)
        }
        updateTransactions()
    }

    fun updateTransactions() = viewModelScope.launch {
        val response = transactionUseCase.getTransactionsByPeriod(
            start = LocalDate.now().minusDays(30),
            finish = LocalDate.now(),
            accountId = getSelectedAccount().value.localId.toInt()
        )
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS ->
                _transactions.value = response.body?.let { VisibleData.Success(it) }
                    ?: VisibleData.Success(emptyList())

            else -> _transactions.value = VisibleData.Error(response.typeResponse)
        }
    }

    fun updateCurrencyOnSelectedAccount(newCurrency: Currency) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = accountUseCase.updateCashAccount(
                AccountStore.selectedAccount.value.copy(currency = newCurrency)
            )
            when (response.typeResponse) {
                ResponseTemplate.TypeResponse.SUCCESS -> {
                    accountManager.setSelectedAccount(
                        AccountStore.selectedAccount.value.copy(currency = newCurrency)
                    )
                    updateAllAccount()
                }

                ResponseTemplate.TypeResponse.UNAUTHORIZED -> ToastController.showToast("Ошибка авторизации")
                ResponseTemplate.TypeResponse.ERROR_CLIENT -> ToastController.showToast("Некорректные данные были отправлены на сервер")
                ResponseTemplate.TypeResponse.ERROR_SERVER -> ToastController.showToast("Ошибка сервера")
                else -> ToastController.showToast("Неизвестная ошибка")
            }
        }

    fun updateNameAndBalanceAccount(accountDomain: AccountDomain) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        val response = accountUseCase.updateCashAccount(accountDomain)
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> {
                updateAllAccount()
            }

            ResponseTemplate.TypeResponse.UNAUTHORIZED -> ToastController.showToast("Ошибка авторизации")
            ResponseTemplate.TypeResponse.ERROR_CLIENT -> ToastController.showToast("Некорректные данные были отправлены на сервер")
            ResponseTemplate.TypeResponse.ERROR_SERVER -> ToastController.showToast("Ошибка сервера")
            else -> ToastController.showToast("Неизвестная ошибка")
        }
    }

    fun createCashAccount(accountDomain: AccountDomain) = viewModelScope.launch(Dispatchers.IO) {
        val response = accountUseCase.createCashAccount(accountDomain)
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> {
                updateAllAccount()
                response.body?.let {
                    accountManager.setSelectedAccount(it)
                }
            }

            ResponseTemplate.TypeResponse.UNAUTHORIZED -> ToastController.showToast("Ошибка авторизации")
            ResponseTemplate.TypeResponse.ERROR_CLIENT -> ToastController.showToast("Некорректные данные были отправлены на сервер")
            ResponseTemplate.TypeResponse.ERROR_SERVER -> ToastController.showToast("Ошибка сервера")
            else -> ToastController.showToast("Неизвестная ошибка")
        }
    }

    fun deleteCashAccount(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        if (AccountStore.selectedAccount.value.id == id) {
            accountManager.setSelectedAccount(allAccount.value.first())
        }
        val response = accountUseCase.deleteCashAccount(id)
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> updateAllAccount()
            ResponseTemplate.TypeResponse.UNAUTHORIZED -> ToastController.showToast("Ошибка авторизации")
            ResponseTemplate.TypeResponse.ERROR_CLIENT -> ToastController.showToast("Некорректные данные были отправлены на сервер")
            ResponseTemplate.TypeResponse.ERROR_SERVER -> ToastController.showToast("Ошибка сервера")
            else -> ToastController.showToast("Неизвестная ошибка")
        }
    }
}