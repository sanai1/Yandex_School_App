package com.example.yandex_school_app.features.category.data.repository

import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.features.category.data.datasource.remote.CategoryRemoteDataSource
import com.example.yandex_school_app.features.category.domain.entity.CategoryDomain
import com.example.yandex_school_app.features.category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
) : CategoryRepository {
    override suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>> {
        return categoryRemoteDataSource.getCategories()
    }
}