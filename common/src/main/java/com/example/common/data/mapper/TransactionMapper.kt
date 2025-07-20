package com.example.common.data.mapper

import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.entity.account.Currency
import com.example.network.model.transaction.response.TransactionResponseNetwork
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.database.model.TransactionModelDB
import com.example.database.model.TransactionWithRelations
import com.example.network.model.transaction.request.TransactionRequestNetwork
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TransactionMapper @Inject constructor(
    private val accountMapper: AccountMapper,
    private val categoryMapper: CategoryMapper
) {
    fun toTransactionDomain(transactionNetwork: TransactionResponseNetwork) = TransactionDomain(
        id = transactionNetwork.id,
        localId = 0,
        accountDomain = transactionNetwork.account.let {
            AccountDomain(
                id = it.id,
                localId = 0,
                name = it.name,
                balance = it.balance,
                currency = Currency.collectionCurrency.find { currency -> currency.abbreviation == it.currency }!!
            )
        },
        categoryDomain = CategoryMapper().toCategoryDomain(transactionNetwork.category),
        amount = transactionNetwork.amount,
        transactionDate = transactionNetwork.transactionDate.let {
            ZonedDateTime.parse(it).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()
        },
        comment = transactionNetwork.comment,
        createDate = transactionNetwork.createdAt.let {
            ZonedDateTime.parse(it).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()
        },
        updateDate = transactionNetwork.updatedAt.let {
            ZonedDateTime.parse(it).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()
        }
    )

    fun toTransactionDomain(transactionWithRelations: TransactionWithRelations) = TransactionDomain(
        id = transactionWithRelations.transactionModelDB.remoteId,
        localId = transactionWithRelations.transactionModelDB.id,
        accountDomain = accountMapper.toAccountDomain(transactionWithRelations.accountModelDB),
        categoryDomain = categoryMapper.toCategoryDomain(transactionWithRelations.categoryModelDB),
        amount = transactionWithRelations.transactionModelDB.amount,
        transactionDate = transactionWithRelations.transactionModelDB.transactionDate,
        comment = transactionWithRelations.transactionModelDB.comment,
        createDate = transactionWithRelations.transactionModelDB.createdAt,
        updateDate = transactionWithRelations.transactionModelDB.updatedAt
    )

    fun toTransactionModelDB(transactionPartDomain: TransactionPartDomain, remoteId: Int) =
        TransactionModelDB(
            remoteId = remoteId,
            amount = transactionPartDomain.amount,
            transactionDate = transactionPartDomain.transactionDate,
            comment = transactionPartDomain.comment,
            createdAt = transactionPartDomain.createdAt ?: LocalDateTime.now(),
            updatedAt = transactionPartDomain.updatedAt ?: LocalDateTime.now(),
            categoryId = transactionPartDomain.categoryId.toLong(),
            accountId = transactionPartDomain.accountId.toLong()
        )

    fun toTransactionPathDomain(transactionNetwork: TransactionRequestNetwork) =
        TransactionPartDomain(
            id = transactionNetwork.id,
            accountId = transactionNetwork.accountId,
            categoryId = transactionNetwork.categoryId,
            amount = transactionNetwork.amount,
            transactionDate = transactionNetwork.transactionDate.let {
                ZonedDateTime.parse(it).withZoneSameInstant(
                    ZoneOffset.UTC
                ).toLocalDateTime()
            },
            comment = transactionNetwork.comment,
            createdAt = transactionNetwork.createdAt?.let {
                ZonedDateTime.parse(it).withZoneSameInstant(
                    ZoneOffset.UTC
                ).toLocalDateTime()
            },
            updatedAt = transactionNetwork.updatedAt?.let {
                ZonedDateTime.parse(it).withZoneSameInstant(
                    ZoneOffset.UTC
                ).toLocalDateTime()
            }
        )

    fun toTransactionPathDomain(
        transactionDomain: TransactionDomain,
        accountId: Int,
        categoryId: Int
    ) = TransactionPartDomain(
        id = transactionDomain.id,
        accountId = accountId,
        categoryId = categoryId,
        amount = transactionDomain.amount,
        transactionDate = transactionDomain.transactionDate,
        comment = transactionDomain.comment ?: "",
        createdAt = transactionDomain.createDate,
        updatedAt = transactionDomain.updateDate
    )

    fun toTransactionRequestNetwork(transactionPartDomain: TransactionPartDomain) =
        TransactionRequestNetwork(
            id = transactionPartDomain.id,
            accountId = transactionPartDomain.accountId,
            categoryId = transactionPartDomain.categoryId,
            amount = transactionPartDomain.amount,
            transactionDate = DateTimeFormatter.ISO_INSTANT.format(
                transactionPartDomain.transactionDate.atZone(
                    ZoneOffset.UTC
                ).toInstant()
            ),
            comment = transactionPartDomain.comment,
            createdAt = transactionPartDomain.createdAt?.toString(),
            updatedAt = transactionPartDomain.updatedAt?.toString()
        )
}