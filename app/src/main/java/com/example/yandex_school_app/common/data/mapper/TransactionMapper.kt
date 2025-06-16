package com.example.yandex_school_app.common.data.mapper

import com.example.yandex_school_app.common.data.network.model.TransactionNetwork
import com.example.yandex_school_app.common.domain.entity.TransactionDomain
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class TransactionMapper @Inject constructor() {
    fun toTransactionDomain(transactionNetwork: TransactionNetwork) = TransactionDomain(
        id = transactionNetwork.id,
        categoryDomain = CategoryMapper().toCategoryDomain(transactionNetwork.category),
        amount = transactionNetwork.amount,
        transactionDate = transactionNetwork.transactionDate.let {
            ZonedDateTime.parse(it).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        },
        comment = transactionNetwork.comment
    )
}