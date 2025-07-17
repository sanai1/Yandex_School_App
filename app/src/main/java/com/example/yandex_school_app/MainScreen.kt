package com.example.yandex_school_app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.cash_account.presentation.AccountViewModel
import com.example.cash_account.presentation.ui.CashAccountScreen
import com.example.cash_account.presentation.ui.CreateCashAccount
import com.example.cash_account.presentation.ui.DetailsCashAccountScreen
import com.example.category.presentation.CategoryViewModel
import com.example.category.presentation.ui.CategoryScreen
import com.example.common.store.TransactionStore
import com.example.expense.presentation.ExpenseViewModel
import com.example.expense.presentation.ui.AnalyticsExpenseScreen
import com.example.expense.presentation.ui.DetailsExpenseScreen
import com.example.expense.presentation.ui.ExpenseScreen
import com.example.expense.presentation.ui.HistoryExpenseScreen
import com.example.income.presentation.ui.DetailsIncomeScreen
import com.example.income.presentation.ui.HistoryIncomeScreen
import com.example.income.presentation.ui.IncomeScreen
import com.example.income.presentation.IncomeViewModel
import com.example.income.presentation.ui.AnalyticsIncomeScreen
import com.example.settings.presentation.SettingsScreen
import com.example.navigation.BottomNavigationBarCustom
import com.example.navigation.NavigationCustomItem
import com.example.navigation.ScreenName
import com.example.navigation.TopBarCustom


@Composable
fun MainScreen(
    navController: NavController,
    selectedItem: NavigationCustomItem,
    modifier: Modifier = Modifier,
    clickChangeTheme: (Boolean) -> Unit = {}
) {
    val isAddAccountClicked = remember { mutableStateOf(false) }
    val isExpenseClicked = remember { mutableStateOf(false) }
    val isIncomeClicked = remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarCustom(
                navController,
                selectedItem,
                clearDetails = {
                    TransactionStore.clear()
                },
                isAddAccountClicked = isAddAccountClicked,
                isExpenseClicked = isExpenseClicked,
                isIncomeClicked = isIncomeClicked
            )
        },
        floatingActionButton = {
            when (selectedItem) {
                is NavigationCustomItem.Expense, is NavigationCustomItem.Income, is NavigationCustomItem.CashAccount -> FloatingActionButton(
                    onClick = {
                        when (selectedItem) {
                            is NavigationCustomItem.Expense -> navController.navigate(ScreenName.DETAILS_EXPENSE)
                            is NavigationCustomItem.Income -> navController.navigate(ScreenName.DETAILS_INCOME)
                            is NavigationCustomItem.CashAccount -> navController.navigate(ScreenName.CREATE_ACCOUNT)
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
                is NavigationCustomItem.Expense -> ExpenseScreen(
                    modifier,
                    onClickDetailsTransaction = {
                        navController.navigate(ScreenName.DETAILS_EXPENSE)
                    },
                    (LocalContext.current as MainActivity).mapViewModel[ExpenseViewModel::class] as ExpenseViewModel
                )

                is NavigationCustomItem.Income -> IncomeScreen(
                    modifier,
                    onClickDetailsTransaction = {
                        navController.navigate(ScreenName.DETAILS_INCOME)
                    },
                    (LocalContext.current as MainActivity).mapViewModel[IncomeViewModel::class] as IncomeViewModel
                )

                is NavigationCustomItem.CashAccount -> CashAccountScreen(
                    modifier,
                    (LocalContext.current as MainActivity).mapViewModel[AccountViewModel::class] as AccountViewModel
                )

                is NavigationCustomItem.Category -> CategoryScreen(
                    modifier,
                    (LocalContext.current as MainActivity).mapViewModel[CategoryViewModel::class] as CategoryViewModel
                )

                is NavigationCustomItem.Settings -> SettingsScreen(
                    modifier,
                    clickChangeTheme = clickChangeTheme
                )

                is NavigationCustomItem.HistoryExpense -> HistoryExpenseScreen(
                    modifier,
                    (LocalContext.current as MainActivity).mapViewModel[ExpenseViewModel::class] as ExpenseViewModel,
                    onClickDetailsTransaction = {
                        navController.navigate(ScreenName.DETAILS_EXPENSE)
                    }
                )

                is NavigationCustomItem.HistoryIncome -> HistoryIncomeScreen(
                    modifier,
                    (LocalContext.current as MainActivity).mapViewModel[IncomeViewModel::class] as IncomeViewModel,
                    onClickDetailsTransaction = {
                        navController.navigate(ScreenName.DETAILS_INCOME)
                    }
                )

                is NavigationCustomItem.DetailsExpense -> DetailsExpenseScreen(
                    modifier,
                    viewModel = (LocalContext.current as MainActivity).mapViewModel[ExpenseViewModel::class] as ExpenseViewModel,
                    isExpenseClicked = isExpenseClicked,
                    callback = {
                        isExpenseClicked.value = false
                    },
                    callbackNavController = {
                        navController.popBackStack()
                    }
                )

                is NavigationCustomItem.DetailsIncome -> DetailsIncomeScreen(
                    modifier,
                    viewModel = (LocalContext.current as MainActivity).mapViewModel[IncomeViewModel::class] as IncomeViewModel,
                    isIncomeClicked = isIncomeClicked,
                    callback = {
                        isIncomeClicked.value = false
                    },
                    callbackNavController = {
                        navController.popBackStack()
                    }
                )

                is NavigationCustomItem.DetailsAccount -> DetailsCashAccountScreen(
                    modifier,
                    (LocalContext.current as MainActivity).mapViewModel[AccountViewModel::class] as AccountViewModel
                )

                is NavigationCustomItem.CrateAccount -> CreateCashAccount(
                    modifier,
                    viewModel = (LocalContext.current as MainActivity).mapViewModel[AccountViewModel::class] as AccountViewModel,
                    isAddAccountClicked = isAddAccountClicked,
                    callbackNavController = {
                        navController.popBackStack()
                    },
                    callback = {
                        isAddAccountClicked.value = false
                    })

                is NavigationCustomItem.AnalyticsExpense -> AnalyticsExpenseScreen(
                    modifier = modifier,
                    viewModel = (LocalContext.current as MainActivity).mapViewModel[ExpenseViewModel::class] as ExpenseViewModel,
                    onClickDetailsTransaction = {
                        navController.navigate(ScreenName.DETAILS_EXPENSE)
                    }
                )

                is NavigationCustomItem.AnalyticsIncome -> AnalyticsIncomeScreen(
                    modifier = modifier,
                    viewModel = (LocalContext.current as MainActivity).mapViewModel[IncomeViewModel::class] as IncomeViewModel,
                    onClickDetailsTransaction = {
                        navController.navigate(ScreenName.DETAILS_INCOME)
                    }
                )
            }
        }
    }
}