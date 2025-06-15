package com.example.yandex_school_app

import com.example.yandex_school_app.features.cash_account.domain.entity.AccountDomain
import com.example.yandex_school_app.common.domain.entity.CategoryDomain
import com.example.yandex_school_app.common.domain.entity.TransactionDomain

object Mok {
    val transactions = listOf(
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Ремонт квартиры",
                emoji = "РК",
                isIncome = false
            ),
            comment = "Ремонт - фурнитура для дверей",
            amount = "100 000 P"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36",
                isIncome = false
            ),
            comment = null,
            amount = "100 000 P"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36",
                isIncome = false
            ),
            comment = null,
            amount = "100 000 P"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36",
                isIncome = false
            ),
            comment = null,
            amount = "100 000 P"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36",
                isIncome = false
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
                emoji = null,
                isIncome = true
            ),
            comment = null,
            amount = "500 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Подработка",
                emoji = null,
                isIncome = true
            ),
            comment = null,
            amount = "100 000 ₽"
        )
    )
    val transactionExpense = listOf(
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Аренда квартиры",
                emoji = "\uD83C\uDFE1",
                isIncome = false
            ),
            comment = null,
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Одежда",
                emoji = "\uD83D\uDC57",
                isIncome = false
            ),
            comment = null,
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36",
                isIncome = false
            ),
            comment = "Джек",
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "На собачку",
                emoji = "\uD83D\uDC36",
                isIncome = false
            ),
            comment = "Энни",
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Ремонт квартиры",
                emoji = "РК",
                isIncome = false
            ),
            comment = null,
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Продукты",
                emoji = "\uD83C\uDF6D",
                isIncome = false
            ),
            comment = null,
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Спортзал",
                emoji = "\uD83C\uDFCB\uFE0F\u200D♂\uFE0F",
                isIncome = false
            ),
            comment = null,
            amount = "100 000 ₽"
        ),
        TransactionDomain(
            categoryDomain = CategoryDomain(
                name = "Медицина",
                emoji = "\uD83D\uDC8A",
                isIncome = false
            ),
            comment = null,
            amount = "100 000 ₽"
        )
    )
}