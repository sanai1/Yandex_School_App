package com.example.network

data class ResponseTemplate<T>(
    val typeResponse: TypeResponse,
    val body: T?
) {
    enum class TypeResponse(
        val text: String
    ) {
        SUCCESS("Данные получены"),
        UNAUTHORIZED("Ошибка авторизации"),
        ERROR_CLIENT("Внутренняя ошибка приложения"),
        NOT_FOUND("Ошибка в данных"),
        MANY_REQUEST("Слишком много запросов к северу.\nПопробуйте позже"),
        ERROR_SERVER("Внутренняя ошибка сервера"),
        NETWORK_PROBLEM("Проблемы с сетью.\nПроверьте подключение к интернету"),
        ALL_BAD("Неизвесная ошибка")
    }
}