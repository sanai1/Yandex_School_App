package com.example.category.data.datasource.local

import com.example.common.domain.entity.category.CategoryDomain
import com.example.network.ResponseTemplate

interface CategoryLocalDataSource {
    suspend fun creteCategory(categoryDomain: CategoryDomain)
    suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>>
    suspend fun getCategoryLocalIdByRemoteId(remoteId: Int): Long
    suspend fun getCategoriesByType(isIncome: Boolean): ResponseTemplate<List<CategoryDomain>>
}