package com.example.common.manager

import com.example.network.di.ApplicationScope
import com.example.common.domain.entity.AccountDomain
import com.example.common.domain.entity.Currency
import javax.inject.Inject

@ApplicationScope
class AccountManager @Inject constructor() {
    private var _selectedAccount: AccountDomain? = null

    fun checkAccount() = _selectedAccount != null

    fun getAccount() = _selectedAccount ?: AccountDomain(
        id = 209,
        name = "Баланс",
        balance = "0",
        currency = Currency.collectionCurrency.first()
    )

    fun setSelectedAccount(selectedAccount: AccountDomain) {
        _selectedAccount = selectedAccount
    }

}