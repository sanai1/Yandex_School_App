package com.example.common.domain.entity.category

data class CategoryDomain(
    val id: Int,
    val localId: Long,
    val name: String,
    val emoji: String?,
    val isIncome: Boolean
)