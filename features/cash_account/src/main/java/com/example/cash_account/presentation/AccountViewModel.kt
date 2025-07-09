package com.example.cash_account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.ResponseTemplate
import com.example.common.presentation.toast.ToastController
import com.example.common.store.AccountStore
import com.example.common.domain.entity.account.AccountDomain
import com.example.cash_account.domain.usecase.AccountUseCase
import com.example.common.domain.entity.account.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase,
    private val accountManager: AccountStore
) : ViewModel() {
    init {
        updateAllAccount()
    }

    private val _allAccount = MutableStateFlow<List<AccountDomain>>(emptyList())
    val allAccount: StateFlow<List<AccountDomain>> = _allAccount.asStateFlow()

    fun updateAllAccount() = viewModelScope.launch(Dispatchers.IO) {
        val response = accountUseCase.getAllCashAccount()
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> {
                response.body?.let { it ->
                    _allAccount.value = it.sortedBy { it.name }
                }
                if (accountManager.checkAccount().not()) {
                    accountManager.setSelectedAccount((response.body as List<AccountDomain>).first())
                } else if (accountManager.selectedAccount.value.id in allAccount.value.map { it.id }) {
                    allAccount.value.find { it.id == accountManager.selectedAccount.value.id }
                        ?.let {
                            accountManager.setSelectedAccount(it)
                        }
                }
            }

            ResponseTemplate.TypeResponse.UNAUTHORIZED -> ToastController.showToast("Ошибка авторизации")
            ResponseTemplate.TypeResponse.ERROR_SERVER -> ToastController.showToast("Ошибка сервера")
            else -> ToastController.showToast("Неизвестная ошибка")
        }
    }

    fun getSelectedAccount() = accountManager.selectedAccount

    fun setSelectedAccountById(newIdAccount: String) {
        allAccount.value.find { it.id.toString() == newIdAccount }?.let {
            accountManager.setSelectedAccount(it)
        }
    }

    fun updateCurrencyOnSelectedAccount(newCurrency: Currency) =
        viewModelScope.launch(Dispatchers.IO) {
            val response = accountUseCase.updateCashAccount(
                accountManager.selectedAccount.value.copy(currency = newCurrency)
            )
            when (response.typeResponse) {
                ResponseTemplate.TypeResponse.SUCCESS -> {
                    updateAllAccount()
                    accountManager.setSelectedAccount(
                        accountManager.selectedAccount.value.copy(currency = newCurrency)
                    )
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
        if (accountManager.selectedAccount.value.id == id) {
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