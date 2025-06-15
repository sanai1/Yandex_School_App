package com.example.yandex_school_app.features.category.data.datasource.remote.impl

import com.example.yandex_school_app.common.data.mapper.CategoryMapper
import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.features.category.data.datasource.remote.CategoryRemoteDataSource
import com.example.yandex_school_app.features.category.data.network.CategoryApiClient
import com.example.yandex_school_app.features.category.domain.entity.CategoryDomain
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(
    private val categoryMapper: CategoryMapper
) : CategoryRemoteDataSource {
    override suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>> {
        val response = CategoryApiClient.categoryApiService.getCategories().execute()
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
}