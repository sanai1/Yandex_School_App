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
        startDestination = "expense"
    ) {
        composable("expense") {
            MainScreen(navController, items["expense"]!!)
        }
        composable("income") {
            MainScreen(navController, items["income"]!!)
        }
        composable("cash_account") {
            MainScreen(navController, items["cash_account"]!!)
        }
        composable("category") {
            MainScreen(navController, items["category"]!!)
        }
        composable("settings") {
            MainScreen(navController, items["settings"]!!)
        }
    }
}