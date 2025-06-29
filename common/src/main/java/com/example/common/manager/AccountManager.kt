package com.example.common.manager

import com.example.common.di.ApplicationScope
import com.example.common.domain.entity.AccountDomain
import javax.inject.Inject

@ApplicationScope
class AccountManager @Inject constructor() {
    private var _accounts: List<AccountDomain> = emptyList()

    fun getAccounts() = _accounts

    fun setAccounts(newAccounts: List<AccountDomain>) {
        _accounts = newAccounts
    }
}