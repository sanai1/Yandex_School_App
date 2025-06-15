package com.example.yandex_school_app.features.cash_account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.di.AccountManager
import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain
import com.example.yandex_school_app.features.cash_account.domain.usecase.AccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase,
    private val accountManager: AccountManager
) : ViewModel() {
    private val _allAccount = MutableStateFlow<List<AccountDomain>>(emptyList())
    val allAccount: StateFlow<List<AccountDomain>> = _allAccount.asStateFlow()

    fun updateAllAccount() = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            accountUseCase.getAllCashAccount()
        }
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> {
                _allAccount.value = response.body!!
                accountManager.setAccounts(response.body)
            }

            else -> {}
        }
    }
}