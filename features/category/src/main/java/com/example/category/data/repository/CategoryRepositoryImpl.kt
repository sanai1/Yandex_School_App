package com.example.category.data.repository

import com.example.category.data.datasource.local.CategoryLocalDataSource
import com.example.network.ResponseTemplate
import com.example.category.data.datasource.remote.CategoryRemoteDataSource
import com.example.common.domain.entity.category.CategoryDomain
import com.example.common.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource,
) : CategoryRepository {
    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (categoryLocalDataSource.getCategories().body?.isEmpty() ?: true) {
                categoryRemoteDataSource.getCategories().body?.forEach { category ->
                    categoryLocalDataSource.creteCategory(category)
                }
            }
        }
    }

    override suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>> {
        return categoryLocalDataSource.getCategories()
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): ResponseTemplate<List<CategoryDomain>> {
        return categoryLocalDataSource.getCategoriesByType(isIncome)
    }
}