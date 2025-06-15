package com.example.yandex_school_app.common.domain.entity

data class TransactionDomain(
    val categoryDomain: CategoryDomain,
    val comment: String?,
    val amount: String
)
