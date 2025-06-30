package com.example.network

data class ResponseTemplate<T>(
    val typeResponse: TypeResponse,
    val body: T?
) {
    enum class TypeResponse {
        SUCCESS,
        UNAUTHORIZED,
        ERROR_CLIENT,
        NOT_FOUND,
        ERROR_SERVER,
        ALL_BAD
    }
}