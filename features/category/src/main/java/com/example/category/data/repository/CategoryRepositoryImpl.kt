package com.example.category.data.repository

import com.example.network.ResponseTemplate
import com.example.category.data.datasource.remote.CategoryRemoteDataSource
import com.example.common.domain.entity.category.CategoryDomain
import com.example.common.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
) : CategoryRepository {
    override suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>> {
        return categoryRemoteDataSource.getCategories()
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): ResponseTemplate<List<CategoryDomain>> {
        return categoryRemoteDataSource.getCategoriesByType(isIncome)
    }
}