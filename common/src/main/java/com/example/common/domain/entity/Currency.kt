package com.example.common.domain.entity

sealed class Currency(
    val name: String,
    val abbreviation: String,
    val symbol: String
) {
    class RUB : Currency(
        name = "Российский рубль ₽",
        abbreviation = "RUB",
        symbol = "₽"
    )

    class USD : Currency(
        name = "Американский доллар $",
        abbreviation = "USD",
        symbol = "$"
    )

    class EUR : Currency(
        name = "Евро €",
        abbreviation = "EUR",
        symbol = "€"
    )

    companion object Example {
        val collectionCurrency = listOf(
            RUB(), USD(), EUR()
        )
    }
}