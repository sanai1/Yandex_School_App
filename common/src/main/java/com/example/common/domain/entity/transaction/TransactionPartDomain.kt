package com.example.common.domain.entity.transaction

import java.time.LocalDateTime

data class TransactionPartDomain(
    val id: Int?,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
