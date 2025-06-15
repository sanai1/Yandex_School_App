package com.example.yandex_school_app.features.category.domain.repository

import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.features.category.domain.entity.CategoryDomain

interface CategoryRepository {
    suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>>
}