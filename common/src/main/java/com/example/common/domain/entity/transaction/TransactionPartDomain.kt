package com.example.common.domain.entity.transaction

import java.time.LocalDateTime

data class TransactionPartDomain(
    val id: Int? = null,
    val accountId: Int,
    val accountLocalId: Int = 0,
    val categoryId: Int,
    val categoryLocalId: Int = 0,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
