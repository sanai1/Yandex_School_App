package com.example.yandex_school_app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.ScreenName
import com.example.navigation.items
import com.example.navigation.secondaryItems
import com.example.common.presentation.toast.ToastMessage
import com.example.common.store.AppTheme

@Composable
fun App(
    onChangeTheme: (Boolean) -> Unit,
    onChangePrimaryColor: (AppTheme.PrimaryColorVariant) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenName.EXPENSE
    ) {
        composable(ScreenName.EXPENSE) {
            MainScreen(navController, items[ScreenName.EXPENSE]!!)
        }
        composable(ScreenName.INCOME) {
            MainScreen(navController, items[ScreenName.INCOME]!!)
        }
        composable(ScreenName.CASH_ACCOUNT) {
            MainScreen(navController, items[ScreenName.CASH_ACCOUNT]!!)
        }
        composable(ScreenName.CATEGORIES) {
            MainScreen(navController, items[ScreenName.CATEGORIES]!!)
        }
        composable(ScreenName.SETTINGS) {
            MainScreen(
                navController,
                items[ScreenName.SETTINGS]!!,
                onChangeTheme = onChangeTheme,
                onChangePrimaryColor = onChangePrimaryColor
            )
        }
        composable(ScreenName.HISTORY_EXPENSE) {
            MainScreen(navController, secondaryItems[ScreenName.HISTORY_EXPENSE]!!)
        }
        composable(ScreenName.HISTORY_INCOME) {
            MainScreen(navController, secondaryItems[ScreenName.HISTORY_INCOME]!!)
        }
        composable(ScreenName.DETAILS_EXPENSE) {
            MainScreen(navController, secondaryItems[ScreenName.DETAILS_EXPENSE]!!)
        }
        composable(ScreenName.DETAILS_INCOME) {
            MainScreen(navController, secondaryItems[ScreenName.DETAILS_INCOME]!!)
        }
        composable(ScreenName.DETAILS_ACCOUNT) {
            MainScreen(navController, secondaryItems[ScreenName.DETAILS_ACCOUNT]!!)
        }
        composable(ScreenName.CREATE_ACCOUNT) {
            MainScreen(navController, secondaryItems[ScreenName.CREATE_ACCOUNT]!!)
        }
        composable(ScreenName.ANALYTICS_EXPENSE) {
            MainScreen(navController, secondaryItems[ScreenName.ANALYTICS_EXPENSE]!!)
        }
        composable(ScreenName.ANALYTICS_INCOME) {
            MainScreen(navController, secondaryItems[ScreenName.ANALYTICS_INCOME]!!)
        }
    }
    ToastMessage()
}