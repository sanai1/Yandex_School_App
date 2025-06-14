package com.example.yandex_school_app.features.cash_account.data.mapper

import com.example.yandex_school_app.features.cash_account.data.AccountNetwork
import com.example.yandex_school_app.features.cash_account.domain.AccountDomain

class AccountMapper {
    fun toAccountDomain(accountNetwork: AccountNetwork) = AccountDomain(
        balance = accountNetwork.balance,
        currency = accountNetwork.currency
    )
}