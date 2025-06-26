package com.example.yandex_school_app.features.cash_account.data.mapper

import com.example.yandex_school_app.features.cash_account.data.network.model.request.AccountRequestNetwork
import com.example.yandex_school_app.features.cash_account.data.network.model.response.AccountResponseNetwork
import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain
import javax.inject.Inject

class AccountMapper @Inject constructor() {
    fun toAccountDomain(accountNetwork: AccountResponseNetwork) = AccountDomain(
        id = accountNetwork.id.toInt(),
        name = accountNetwork.name,
        balance = accountNetwork.balance,
        currency = accountNetwork.currency
    )

    fun toAccountRequest(accountDomain: AccountDomain) = AccountRequestNetwork(
        name = accountDomain.name,
        balance = accountDomain.balance,
        currency = accountDomain.currency
    )
}