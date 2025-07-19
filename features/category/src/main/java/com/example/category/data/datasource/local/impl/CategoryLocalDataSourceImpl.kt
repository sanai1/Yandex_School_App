package com.example.category.data.datasource.local.impl

import com.example.category.data.datasource.local.CategoryLocalDataSource
import com.example.common.data.mapper.CategoryMapper
import com.example.common.domain.entity.category.CategoryDomain
import com.example.database.dao.CategoryDao
import com.example.network.ResponseTemplate
import javax.inject.Inject

class CategoryLocalDataSourceImpl @Inject constructor(
    private val categoryMapper: CategoryMapper,
    private val categoryDao: CategoryDao
) : CategoryLocalDataSource {
    override suspend fun creteCategory(categoryDomain: CategoryDomain) {
        categoryDao.insert(categoryMapper.toCategoryModelDB(categoryDomain))
    }

    override suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>> {
        return try {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = categoryDao.getAll().map { categoryMapper.toCategoryDomain(it) }
            )
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }

    override suspend fun getCategoryLocalIdByRemoteId(remoteId: Int): Long {
        return try {
            categoryDao.getCategoryLocalIdByRemoteId(remoteId.toLong())
        } catch (_: Exception) {
            0
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): ResponseTemplate<List<CategoryDomain>> {
        return try {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = categoryDao.getCategoryByType(isIncome).map {
                    categoryMapper.toCategoryDomain(it)
                }
            )
        } catch (_: Exception) {
            ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )
        }
    }
}