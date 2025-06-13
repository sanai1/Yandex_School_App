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
import androidx.compose.runtime.mutableIntStateOf
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
import com.airbnb.lottie.compose.*
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.yandex_school_app.ui.theme.Yandex_School_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            Yandex_School_AppTheme {
                val showSplash = remember { mutableStateOf(true) }
                if (showSplash.value) {
                    SplashScreen {
                        showSplash.value = false
                    }
                } else {
                    MainScreen()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val items = listOf(
        NavigationItemCustom(
            title = "Расходы",
            icon = painterResource(R.drawable.expense_selected),
        ),
        NavigationItemCustom(
            title = "Доходы",
            icon = painterResource(R.drawable.income_selected),
        ),
        NavigationItemCustom(
            title = "Счет",
            icon = painterResource(R.drawable.cash_account_selected),
        ),
        NavigationItemCustom(
            title = "Статьи",
            icon = painterResource(R.drawable.articles_selected),
        ),
        NavigationItemCustom(
            title = "Настройки",
            icon = painterResource(R.drawable.settings_selected),
        )
    )
    var selectedItem = remember { mutableIntStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            when (selectedItem.intValue) {
                                0 -> "Расходы сегодня"
                                1 -> "Доходы сегодня"
                                2 -> "Мой счет"
                                3 -> "Мои статьи"
                                4 -> "Настройки"
                                else -> ""
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    when (selectedItem.intValue) {
                        0, 1 -> IconButton(onClick = {}) {
                            Icon(Icons.Default.Refresh, contentDescription = "")
                        }

                        2 -> IconButton(onClick = {}) {
                            Icon(Icons.Default.Create, contentDescription = "")
                        }
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.title,
                                tint = if (selectedItem.intValue == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                            )
                        },
                        label = { Text(item.title) },
                        selected = selectedItem.intValue == index,
                        onClick = { selectedItem.intValue = index },
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
            when (selectedItem.intValue) {
                0 -> ExpenseScreen(listTransactionDomain = Mok.transactionExpense, modifier)
                1 -> IncomeScreen(listTransactionDomain = Mok.transactionIncome, modifier)
                2 -> CashAccountScreen(accountDomain = Mok.account, modifier)
                3 -> CategoryScreen(listCategory = Mok.categories, modifier)
                4 -> SettingsScreen(modifier)
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