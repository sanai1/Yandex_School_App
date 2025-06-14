package com.example.yandex_school_app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.yandex_school_app.Mok
import com.example.yandex_school_app.features.cash_account.presentation.CashAccountScreen
import com.example.yandex_school_app.features.category.presentation.CategoryScreen
import com.example.yandex_school_app.features.expense.presentation.ExpenseScreen
import com.example.yandex_school_app.features.expense.presentation.HistoryExpenseScreen
import com.example.yandex_school_app.features.income.presentation.HistoryIncomeScreen
import com.example.yandex_school_app.features.income.presentation.IncomeScreen
import com.example.yandex_school_app.features.settings.presentation.SettingsScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    selectedItem: NavigationCustomItem,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarCustom(navController, selectedItem)
        },
        bottomBar = {
            BottomNavigationBarCustom(navController, selectedItem)
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedItem) {
                is NavigationCustomItem.Expense -> ExpenseScreen(
                    listTransactionDomain = Mok.transactionExpense,
                    modifier
                )

                is NavigationCustomItem.Income -> IncomeScreen(
                    listTransactionDomain = Mok.transactionIncome,
                    modifier
                )

                is NavigationCustomItem.CashAccount -> CashAccountScreen(
                    accountDomain = Mok.account,
                    modifier
                )

                is NavigationCustomItem.Category -> CategoryScreen(
                    listCategory = Mok.categories,
                    modifier
                )

                is NavigationCustomItem.Settings -> SettingsScreen(modifier)

                is NavigationCustomItem.HistoryExpense -> HistoryExpenseScreen(navController)

                is NavigationCustomItem.HistoryIncome -> HistoryIncomeScreen(navController)
            }
        }
    }
}