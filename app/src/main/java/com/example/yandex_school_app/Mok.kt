package com.example.yandex_school_app

import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain
import com.example.yandex_school_app.common.domain.entity.CategoryDomain
import com.example.yandex_school_app.common.domain.entity.TransactionDomain

object Mok {
    val transactions = listOf(
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Ремонт квартиры",
                emoji = "РК"
            ),
            comment = "Ремонт - фурнитура для дверей",
            amount = "100 000 P"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36"
            ),
            comment = null,
            amount = "100 000 P"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36"
            ),
            comment = null,
            amount = "100 000 P"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36"
            ),
            comment = null,
            amount = "100 000 P"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36"
            ),
            comment = null,
            amount = "100 000 P"
        )
    )
    val account = AccountDomain(
        id = 0,
        balance = "-670 000 ₽",
        currency = "₽"
    )
    val transactionIncome = listOf(
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Зарплата",
                emoji = null
            ),
            comment = null,
            amount = "500 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Подработка",
                emoji = null
            ),
            comment = null,
            amount = "100 000 ₽"
        )
    )
    val transactionExpense = listOf(
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Аренда квартиры",
                emoji = "\uD83C\uDFE1"
            ),
            comment = null,
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Одежда",
                emoji = "\uD83D\uDC57"
            ),
            comment = null,
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36"
            ),
            comment = "Джек",
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36"
            ),
            comment = "Энни",
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Ремонт квартиры",
                emoji = "РК"
            ),
            comment = null,
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Продукты",
                emoji = "\uD83C\uDF6D"
            ),
            comment = null,
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Спортзал",
                emoji = "\uD83C\uDFCB\uFE0F\u200D♂\uFE0F"
            ),
            comment = null,
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Медицина",
                emoji = "\uD83D\uDC8A"
            ),
            comment = null,
            amount = "100 000 ₽"
        )
    )
}