package com.example.yandex_school_app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.yandex_school_app.Mok
import com.example.yandex_school_app.features.cash_account.presentation.CashAccountScreen
import com.example.yandex_school_app.features.category.presentation.CategoryScreen
import com.example.yandex_school_app.features.expense.presentation.ExpenseScreen
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
            TopAppBar(
                title = {
                    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            when (selectedItem) {
                                is NavigationCustomItem.Expense -> "Расходы сегодня"
                                is NavigationCustomItem.Income -> "Доходы сегодня"
                                is NavigationCustomItem.CashAccount -> "Мой счет"
                                is NavigationCustomItem.Category -> "Мои статьи"
                                is NavigationCustomItem.Settings -> "Настройки"
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    when (selectedItem) {
                        is NavigationCustomItem.Expense, is NavigationCustomItem.Income -> IconButton(
                            onClick = {}) {
                            Icon(Icons.Default.Refresh, contentDescription = "")
                        }

                        is NavigationCustomItem.CashAccount -> IconButton(onClick = {}) {
                            Icon(Icons.Default.Create, contentDescription = "")
                        }

                        else -> {}
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEach { element ->
                    val item = element.value
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = stringResource(item.title),
                                tint = if (selectedItem == item) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                            )
                        },
                        label = { Text(stringResource(item.title)) },
                        selected = selectedItem == item,
                        onClick = {
                            navController.navigate(element.key)
                        },
                        alwaysShowLabel = true
                    )
                }
            }
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
            }
        }
    }
}