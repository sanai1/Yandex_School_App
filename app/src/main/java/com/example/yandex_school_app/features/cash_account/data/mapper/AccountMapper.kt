package com.example.yandex_school_app.features.cash_account.data.mapper

import com.example.yandex_school_app.features.cash_account.data.network.model.AccountNetwork
import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain
import javax.inject.Inject

class AccountMapper @Inject constructor() {
    fun toAccountDomain(accountNetwork: AccountNetwork) = AccountDomain(
        id = accountNetwork.id.toInt(),
        balance = accountNetwork.balance,
        currency = accountNetwork.currency
    )
}