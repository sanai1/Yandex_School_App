package com.example.category.data.datasource.remote.impl

import com.example.common.data.mapper.CategoryMapper
import com.example.common.data.network.ResponseTemplate
import com.example.category.data.datasource.remote.CategoryRemoteDataSource
import com.example.category.data.network.CategoryApiClient
import com.example.common.domain.entity.CategoryDomain
import kotlinx.coroutines.delay
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(
    private val categoryMapper: CategoryMapper
) : CategoryRemoteDataSource {
    override suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>> {
        var response = networkCategories()
        repeat(3) {
            if (response.code() == 500) {
                delay(2000)
                response = networkCategories()
            } else return@repeat
        }
        return when (response.code()) {
            200, 201, 204 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.SUCCESS,
                body = response.body()?.map { categoryMapper.toCategoryDomain(it) }
            )

            400 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_CLIENT,
                body = null
            )

            401 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.UNAUTHORIZED,
                body = null
            )

            404 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.NOT_FOUND,
                body = null
            )

            500 -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_SERVER,
                body = null
            )

            else -> ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ALL_BAD,
                body = null
            )
        }
    }

    private fun networkCategories() = CategoryApiClient.categoryApiService.getCategories().execute()
}