package com.example.yandex_school_app

sealed class NavigationCustomItem(
    val title: Int, val icon: Int
) {
    class Expense : NavigationCustomItem(
        title = R.string.menu_expense,
        icon = R.drawable.expense_selected
    )

    class Income : NavigationCustomItem(
        title = R.string.menu_income,
        icon = R.drawable.income_selected
    )

    class CashAccount : NavigationCustomItem(
        title = R.string.menu_cash_account,
        icon = R.drawable.cash_account_selected
    )

    class Category : NavigationCustomItem(
        title = R.string.menu_category,
        icon = R.drawable.articles_selected
    )

    class Settings : NavigationCustomItem(
        title = R.string.menu_settings,
        icon = R.drawable.settings_selected
    )
}