package com.example.category.data.repository

import com.example.common.data.network.ResponseTemplate
import com.example.category.data.datasource.remote.CategoryRemoteDataSource
import com.example.common.domain.entity.CategoryDomain
import com.example.category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
) : CategoryRepository {
    override suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>> {
        return categoryRemoteDataSource.getCategories()
    }
}