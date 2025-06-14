package com.example.yandex_school_app.common.data.mapper

import com.example.yandex_school_app.common.data.TransactionNetwork
import com.example.yandex_school_app.common.domain.TransactionDomain

class TransactionMapper {
    fun toTransactionDomain(transactionNetwork: TransactionNetwork) = TransactionDomain(
        categoryDomain = CategoryMapper().toCategoryDomain(transactionNetwork.category),
        comment = transactionNetwork.comment,
        amount = transactionNetwork.amount
    )
}