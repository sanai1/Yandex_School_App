package com.example.common.domain.entity

import com.example.common.R

sealed class Currency(
    val name: String,
    val abbreviation: String,
    val symbol: String,
    val icon: Int
) {
    class RUB : Currency(
        name = "Российский рубль ₽",
        abbreviation = "RUB",
        symbol = "₽",
        icon = R.drawable.rub
    )

    class USD : Currency(
        name = "Американский доллар $",
        abbreviation = "USD",
        symbol = "$",
        icon = R.drawable.usd
    )

    class EUR : Currency(
        name = "Евро €",
        abbreviation = "EUR",
        symbol = "€",
        icon = R.drawable.eur
    )

    companion object Example {
        val collectionCurrency = listOf(
            RUB(), USD(), EUR()
        )
    }
}