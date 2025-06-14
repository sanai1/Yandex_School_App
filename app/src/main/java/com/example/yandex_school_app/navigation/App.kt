package com.example.yandex_school_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App() {
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
            MainScreen(navController, items[ScreenName.SETTINGS]!!)
        }
        composable(ScreenName.HISTORY_EXPENSE) {
            MainScreen(navController, secondaryItems[ScreenName.HISTORY_EXPENSE]!!)
        }
        composable(ScreenName.HISTORY_INCOME) {
            MainScreen(navController, secondaryItems[ScreenName.HISTORY_INCOME]!!)
        }
    }
}