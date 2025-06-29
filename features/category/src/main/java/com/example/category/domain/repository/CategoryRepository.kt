package com.example.category.domain.repository

import com.example.common.data.network.ResponseTemplate
import com.example.common.domain.entity.CategoryDomain

interface CategoryRepository {
    suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>>
}