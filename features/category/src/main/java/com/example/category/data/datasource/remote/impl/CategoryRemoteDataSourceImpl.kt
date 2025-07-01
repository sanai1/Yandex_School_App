package com.example.category.data.datasource.remote.impl

import com.example.common.data.mapper.CategoryMapper
import com.example.network.ResponseTemplate
import com.example.category.data.datasource.remote.CategoryRemoteDataSource
import com.example.common.domain.entity.CategoryDomain
import com.example.network.check.NoConnectivityException
import com.example.network.service.CategoryApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(
    private val categoryMapper: CategoryMapper,
    private val categoryApiService: CategoryApiService
) : CategoryRemoteDataSource {
    override suspend fun getCategories(): ResponseTemplate<List<CategoryDomain>> {
        try {
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
        } catch (_: NoConnectivityException) {
            return ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.NETWORK_PROBLEM,
                body = null
            )
        } catch (_: Exception) {
            return ResponseTemplate(
                typeResponse = ResponseTemplate.TypeResponse.ERROR_SERVER,
                body = null
            )
        }
    }

    private fun networkCategories() = categoryApiService.getCategories().execute()
}