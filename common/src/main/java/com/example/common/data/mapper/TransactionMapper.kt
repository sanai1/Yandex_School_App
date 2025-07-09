package com.example.common.data.mapper

import com.example.network.model.transaction.response.TransactionResponseNetwork
import com.example.common.domain.entity.transaction.TransactionDomain
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
}