package com.example.yandex_school_app.features.cash_account.data.network.model

/**
 * Данная модель необходима для построения графика -> маппер для нее буду делать когда будет ясна структура графика
 */
data class AccountStatistics(
    val id: Long,
    val name: String,
    val currency: String,
    val incomeStats: List<Stat>,
    val expenseStats: List<Stat>,
    val createdAt: String,
    val updatedAt: String
) {
    data class Stat(
        val categoryId: Long,
        val categoryName: String,
        val emoji: String,
        val amount: String
    )
}