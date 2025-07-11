package com.example.cash_account.data.mapper

import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.entity.account.Currency
import com.example.network.model.cash_account.request.AccountRequestNetwork
import com.example.network.model.cash_account.response.AccountResponseNetwork
import javax.inject.Inject

class AccountMapper @Inject constructor() {
    fun toAccountDomain(accountNetwork: AccountResponseNetwork) = AccountDomain(
        id = accountNetwork.id.toInt(),
        name = accountNetwork.name,
        balance = accountNetwork.balance,
        currency = Currency.collectionCurrency.find { it.abbreviation == accountNetwork.currency }!!
    )

    fun toAccountRequest(accountDomain: AccountDomain) = AccountRequestNetwork(
        name = accountDomain.name,
        balance = accountDomain.balance,
        currency = accountDomain.currency.abbreviation
    )
}