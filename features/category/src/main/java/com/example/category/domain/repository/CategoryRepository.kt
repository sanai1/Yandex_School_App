package com.example.category.domain.repository

import com.example.network.ResponseTemplate
import com.example.common.domain.entity.category.CategoryDomain

interface CategoryRepository {
    suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>>
}