package com.example.common.data.mapper

import com.example.network.model.transaction.response.TransactionResponseNetwork
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.network.model.transaction.request.TransactionRequestNetwork
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class TransactionMapper @Inject constructor() {
    fun toTransactionDomain(transactionNetwork: TransactionResponseNetwork) = TransactionDomain(
        id = transactionNetwork.id,
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
            transactionDate = transactionPartDomain.transactionDate.toString(),
            comment = transactionPartDomain.comment,
            createdAt = transactionPartDomain.createdAt?.toString(),
            updatedAt = transactionPartDomain.updatedAt?.toString()
        )
}