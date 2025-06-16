package com.example.yandex_school_app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.yandex_school_app.features.cash_account.presentation.ui.CashAccountScreen
import com.example.yandex_school_app.features.cash_account.presentation.ui.DetailsCashAccountScreen
import com.example.yandex_school_app.features.category.presentation.CategoryScreen
import com.example.yandex_school_app.features.expense.presentation.ui.DetailsExpenseScreen
import com.example.yandex_school_app.features.expense.presentation.ui.ExpenseScreen
import com.example.yandex_school_app.features.expense.presentation.ui.HistoryExpenseScreen
import com.example.yandex_school_app.features.income.presentation.ui.DetailsIncomeScreen
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
        floatingActionButton = {
            when (selectedItem) {
                is NavigationCustomItem.Expense, is NavigationCustomItem.Income, is NavigationCustomItem.CashAccount -> FloatingActionButton(
                    onClick = {
                        when (selectedItem) {
                            is NavigationCustomItem.Expense -> navController.navigate(ScreenName.DETAILS_EXPENSE)
                            is NavigationCustomItem.Income -> navController.navigate(ScreenName.DETAILS_INCOME)
                            is NavigationCustomItem.CashAccount -> navController.navigate(ScreenName.DETAILS_ACCOUNT)
                            else -> {}
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = modifier.clip(CircleShape)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "", tint = Color.White)
                }

                else -> {}
            }
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

                is NavigationCustomItem.DetailsExpense -> DetailsExpenseScreen(modifier)

                is NavigationCustomItem.DetailsIncome -> DetailsIncomeScreen(modifier)

                is NavigationCustomItem.DetailsAccount -> DetailsCashAccountScreen(modifier)
            }
        }
    }
}