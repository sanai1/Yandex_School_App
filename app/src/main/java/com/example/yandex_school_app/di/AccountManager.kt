package com.example.yandex_school_app.di

import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain
import javax.inject.Inject

@ApplicationScope
class AccountManager @Inject constructor() {
    private var _accounts: List<AccountDomain> = emptyList()

    fun getAccounts() = _accounts

    fun setAccounts(newAccounts: List<AccountDomain>) {
        _accounts = newAccounts
    }
}