package com.example.common.data.mapper

import com.example.network.ResponseTemplate
import javax.inject.Inject

class ResponseCodeMapper @Inject constructor() {
    fun mapResponseCode(code: Int) = when(code) {
        200, 201, 204 -> ResponseTemplate.TypeResponse.SUCCESS
        400 -> ResponseTemplate.TypeResponse.ERROR_CLIENT
        401 -> ResponseTemplate.TypeResponse.UNAUTHORIZED
        404 -> ResponseTemplate.TypeResponse.NOT_FOUND
        429 -> ResponseTemplate.TypeResponse.MANY_REQUEST
        500 -> ResponseTemplate.TypeResponse.ERROR_SERVER
        else -> ResponseTemplate.TypeResponse.ALL_BAD
    }
}