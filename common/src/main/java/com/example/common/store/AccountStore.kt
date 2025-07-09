package com.example.common.store

import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.entity.account.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AccountStore @Inject constructor() {
    private val _selectedAccount = MutableStateFlow(accountExample)
    val selectedAccount: StateFlow<AccountDomain> = _selectedAccount.asStateFlow()

    fun checkAccount() = _selectedAccount.value != accountExample

    fun setSelectedAccount(selectedAccount: AccountDomain) {
        _selectedAccount.value = selectedAccount
    }

    companion object Example {
        val accountExample = AccountDomain(
            id = 209,
            name = "Баланс",
            balance = "0",
            currency = Currency.collectionCurrency.first()
        )
    }
}