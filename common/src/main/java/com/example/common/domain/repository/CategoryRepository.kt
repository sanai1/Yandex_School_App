package com.example.common.domain.repository

import com.example.common.domain.entity.category.CategoryDomain
import com.example.network.ResponseTemplate

interface CategoryRepository {
    suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>>
    suspend fun getCategoriesByType(isIncome: Boolean): ResponseTemplate<List<CategoryDomain>>
}