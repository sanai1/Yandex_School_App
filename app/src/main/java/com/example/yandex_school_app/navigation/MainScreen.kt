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
import com.example.yandex_school_app.features.expense.presentation.ui.ExpenseScreen
import com.example.yandex_school_app.features.expense.presentation.ui.HistoryExpenseScreen
import com.example.yandex_school_app.features.income.presentation.ui.HistoryIncomeScreen
import com.example.yandex_school_app.features.income.presentation.ui.IncomeScreen
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
                is NavigationCustomItem.Expense -> ExpenseScreen(modifier)

                is NavigationCustomItem.Income -> IncomeScreen(modifier)

                is NavigationCustomItem.CashAccount -> CashAccountScreen(modifier)

                is NavigationCustomItem.Category -> CategoryScreen(modifier)

                is NavigationCustomItem.Settings -> SettingsScreen(modifier)

                is NavigationCustomItem.HistoryExpense -> HistoryExpenseScreen(modifier)

                is NavigationCustomItem.HistoryIncome -> HistoryIncomeScreen(modifier)
            }
        }
    }
}