package com.example.yandex_school_app.common.data.mapper

import com.example.yandex_school_app.common.data.network.model.TransactionNetwork
import com.example.yandex_school_app.common.domain.entity.TransactionDomain
import javax.inject.Inject

class TransactionMapper @Inject constructor() {
    fun toTransactionDomain(transactionNetwork: TransactionNetwork) = TransactionDomain(
        categoryDomain = CategoryMapper().toCategoryDomain(transactionNetwork.category),
        comment = transactionNetwork.comment,
        amount = transactionNetwork.amount
    )
}