package com.example.common.domain.entity.transaction

import com.example.common.domain.entity.category.CategoryDomain
import java.time.LocalDateTime

data class TransactionDomain(
    val id: Long,
    val categoryDomain: CategoryDomain,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String?
)