package com.example.common.data.mapper

import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.entity.account.Currency
import com.example.network.model.transaction.response.TransactionResponseNetwork
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.network.model.transaction.request.TransactionRequestNetwork
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TransactionMapper @Inject constructor() {
    fun toTransactionDomain(transactionNetwork: TransactionResponseNetwork) = TransactionDomain(
        id = transactionNetwork.id,
        accountDomain = transactionNetwork.account.let {
            AccountDomain(
                id = it.id,
                name = it.name,
                balance = it.balance,
                currency = Currency.collectionCurrency.find { currency -> currency.abbreviation == it.currency }!!
            )
        },
        categoryDomain = CategoryMapper().toCategoryDomain(transactionNetwork.category),
        amount = transactionNetwork.amount,
        transactionDate = transactionNetwork.transactionDate.let {
            ZonedDateTime.parse(it).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        },
        comment = transactionNetwork.comment
    )

    fun toTransactionPathDomain(transactionNetwork: TransactionRequestNetwork) =
        TransactionPartDomain(
            id = transactionNetwork.id,
            accountId = transactionNetwork.accountId,
            categoryId = transactionNetwork.categoryId,
            amount = transactionNetwork.amount,
            transactionDate = transactionNetwork.transactionDate.let {
                ZonedDateTime.parse(it).withZoneSameInstant(
                    ZoneId.systemDefault()
                ).toLocalDateTime()
            },
            comment = transactionNetwork.comment,
            createdAt = transactionNetwork.createdAt?.let {
                ZonedDateTime.parse(it).withZoneSameInstant(
                    ZoneId.systemDefault()
                ).toLocalDateTime()
            },
            updatedAt = transactionNetwork.updatedAt?.let {
                ZonedDateTime.parse(it).withZoneSameInstant(
                    ZoneId.systemDefault()
                ).toLocalDateTime()
            }
        )

    fun toTransactionRequestNetwork(transactionPartDomain: TransactionPartDomain) =
        TransactionRequestNetwork(
            id = transactionPartDomain.id,
            accountId = transactionPartDomain.accountId,
            categoryId = transactionPartDomain.categoryId,
            amount = transactionPartDomain.amount,
            transactionDate = DateTimeFormatter.ISO_INSTANT.format(
                transactionPartDomain.transactionDate.atZone(
                    ZoneId.systemDefault()
                ).toInstant()
            ),
            comment = transactionPartDomain.comment,
            createdAt = transactionPartDomain.createdAt?.toString(),
            updatedAt = transactionPartDomain.updatedAt?.toString()
        )
}