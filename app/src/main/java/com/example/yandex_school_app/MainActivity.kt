package com.example.yandex_school_app

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.yandex_school_app.category.presentation.CategoryScreen
import com.example.yandex_school_app.cash_account.presentation.CashAccountScreen
import com.example.yandex_school_app.expense.presentation.ExpenseScreen
import com.example.yandex_school_app.income.presentation.IncomeScreen
import com.example.yandex_school_app.settings.presentation.SettingsScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.yandex_school_app.ui.theme.Yandex_School_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yandex_School_AppTheme {
                val showSplash = remember { mutableStateOf(true) }
                if (showSplash.value) {
                    SplashScreen {
                        showSplash.value = false
                    }
                } else {
                    App()
                }
            }
        }
    }
}

val items = mapOf(
    "expense" to NavigationCustomItem.Expense(),
    "income" to NavigationCustomItem.Income(),
    "cash_account" to NavigationCustomItem.CashAccount(),
    "category" to NavigationCustomItem.Category(),
    "settings" to NavigationCustomItem.Settings()
)

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

@Composable
fun SplashScreen(onEnd: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.splash_animation)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )

    LaunchedEffect(progress) {
        if (progress >= 0.99f) {
            onEnd()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize()
        )
    }
}