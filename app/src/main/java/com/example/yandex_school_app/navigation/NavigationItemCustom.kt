package com.example.yandex_school_app.navigation

import com.example.yandex_school_app.R

sealed class NavigationCustomItem(
    val title: Int, val icon: Int
) {
    class Expense : NavigationCustomItem(
        title = R.string.menu_expense,
        icon = R.drawable.expense_selected
    )

    class HistoryExpense : NavigationCustomItem(
        title = R.string.menu_expense,
        icon = R.drawable.expense_selected
    )

    class DetailsExpense : NavigationCustomItem(
        title = R.string.menu_expense,
        icon = R.drawable.expense_selected
    )

    class Income : NavigationCustomItem(
        title = R.string.menu_income,
        icon = R.drawable.income_selected
    )

    class HistoryIncome : NavigationCustomItem(
        title = R.string.menu_income,
        icon = R.drawable.income_selected
    )

    class DetailsIncome : NavigationCustomItem(
        title = R.string.menu_income,
        icon = R.drawable.income_selected
    )

    class CashAccount : NavigationCustomItem(
        title = R.string.menu_cash_account,
        icon = R.drawable.cash_account_selected
    )

    class DetailsAccount : NavigationCustomItem(
        title = R.string.menu_cash_account,
        icon = R.drawable.cash_account_selected
    )

    class CrateAccount: NavigationCustomItem(
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

val items = mapOf(
    ScreenName.EXPENSE to NavigationCustomItem.Expense(),
    ScreenName.INCOME to NavigationCustomItem.Income(),
    ScreenName.CASH_ACCOUNT to NavigationCustomItem.CashAccount(),
    ScreenName.CATEGORIES to NavigationCustomItem.Category(),
    ScreenName.SETTINGS to NavigationCustomItem.Settings()
)

val secondaryItems = mapOf(
    ScreenName.HISTORY_EXPENSE to NavigationCustomItem.HistoryExpense(),
    ScreenName.HISTORY_INCOME to NavigationCustomItem.HistoryIncome(),
    ScreenName.DETAILS_EXPENSE to NavigationCustomItem.DetailsExpense(),
    ScreenName.DETAILS_INCOME to NavigationCustomItem.DetailsIncome(),
    ScreenName.DETAILS_ACCOUNT to NavigationCustomItem.DetailsAccount(),
    ScreenName.CREATE_ACCOUNT to NavigationCustomItem.CrateAccount()
)