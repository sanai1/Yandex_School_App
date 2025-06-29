package com.example.yandex_school_app.features.category.data.datasource.remote

import com.example.common.data.network.ResponseTemplate
import com.example.common.domain.entity.CategoryDomain

interface CategoryRemoteDataSource {
    suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>>
}