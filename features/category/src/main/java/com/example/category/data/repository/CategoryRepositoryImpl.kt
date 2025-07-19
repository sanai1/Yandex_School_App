package com.example.category.data.repository

import com.example.category.data.datasource.local.CategoryLocalDataSource
import com.example.network.ResponseTemplate
import com.example.common.domain.entity.category.CategoryDomain
import com.example.common.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryLocalDataSource: CategoryLocalDataSource,
) : CategoryRepository {
    override suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>> {
        return categoryLocalDataSource.getCategories()
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): ResponseTemplate<List<CategoryDomain>> {
        return categoryLocalDataSource.getCategoriesByType(isIncome)
    }
}