package com.example.yandex_school_app.common.domain.entity

import java.time.LocalDateTime

data class TransactionDomain(
    val id: Long,
    val categoryDomain: CategoryDomain,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String?
)
