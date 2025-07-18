package com.example.cash_account.data.mapper

import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.entity.account.Currency
import com.example.database.model.AccountModelDB
import com.example.network.model.cash_account.request.AccountRequestNetwork
import com.example.network.model.cash_account.response.AccountResponseNetwork
import javax.inject.Inject

class AccountMapper @Inject constructor() {
    fun toAccountDomain(accountNetwork: AccountResponseNetwork) = AccountDomain(
        id = accountNetwork.id.toInt(),
        localId = 0,
        name = accountNetwork.name,
        balance = accountNetwork.balance,
        currency = Currency.collectionCurrency.find { it.abbreviation == accountNetwork.currency }!!
    )

    fun toAccountDomain(accountModelDB: AccountModelDB) = AccountDomain(
        id = accountModelDB.remoteId.toInt(),
        localId = accountModelDB.id,
        name = accountModelDB.name,
        balance = accountModelDB.balance,
        currency = Currency.collectionCurrency.first { it.abbreviation == accountModelDB.currency }
    )

    fun toAccountRequest(accountDomain: AccountDomain) = AccountRequestNetwork(
        name = accountDomain.name,
        balance = accountDomain.balance,
        currency = accountDomain.currency.abbreviation
    )

    fun toAccountModelDB(accountDomain: AccountDomain) = AccountModelDB(
        id = accountDomain.localId,
        remoteId = accountDomain.id.toLong(),
        name = accountDomain.name,
        balance = accountDomain.balance,
        currency = accountDomain.currency.abbreviation
    )
}