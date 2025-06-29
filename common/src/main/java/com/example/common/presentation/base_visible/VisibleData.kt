package com.example.common.presentation.base_visible

import com.example.common.data.network.ResponseTemplate

sealed class VisibleData<T> {
    class Loading<T> : VisibleData<T>()
    data class Success<T>(
        val data: T
    ) : VisibleData<T>()

    data class Error<T>(
        val type: ResponseTemplate.TypeResponse,
        val message: String? = null
    ) : VisibleData<T>()
}