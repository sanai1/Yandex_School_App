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
        MANY_REQUEST,
        ERROR_SERVER,
        NETWORK_PROBLEM,
        ALL_BAD
    }
}