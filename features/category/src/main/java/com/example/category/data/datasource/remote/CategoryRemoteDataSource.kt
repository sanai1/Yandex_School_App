package com.example.category.data.datasource.remote

import com.example.common.data.network.ResponseTemplate
import com.example.common.domain.entity.CategoryDomain

interface CategoryRemoteDataSource {
    suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>>
}