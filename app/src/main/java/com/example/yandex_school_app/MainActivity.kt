package com.example.yandex_school_app

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
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
import com.example.yandex_school_app.ui.screens.ArticlesScreen
import com.example.yandex_school_app.ui.screens.CashAccountScreen
import com.example.yandex_school_app.ui.screens.ExpenseScreen
import com.example.yandex_school_app.ui.screens.IncomeScreen
import com.example.yandex_school_app.ui.screens.SettingsScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.yandex_school_app.ui.theme.Yandex_School_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yandex_School_AppTheme {
                MainScreen()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedItem = remember { mutableIntStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
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
                },
                actions = {

                }
            )
        },
        bottomBar = {
            val items = listOf("Расходы", "Доходы", "Счет", "Статьи", "Настройки")
            val selectedIcons = listOf(
                Icons.Filled.Home,
                Icons.Filled.Favorite,
                Icons.Filled.AccountBox,
                Icons.Filled.Star,
                Icons.Filled.Build
            )
            val unselectedIcons = listOf(
                Icons.Outlined.Home,
                Icons.Outlined.Favorite,
                Icons.Outlined.AccountBox,
                Icons.Outlined.Star,
                Icons.Outlined.Build
            )
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                if (selectedItem.intValue == index) selectedIcons[index] else unselectedIcons[index],
                                contentDescription = item
                            )
                        },
                        label = { Text(item) },
                        selected = selectedItem.intValue == index,
                        onClick = { selectedItem.intValue = index },
                        alwaysShowLabel = false
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
                0 -> ExpenseScreen(modifier)
                1 -> IncomeScreen(modifier)
                2 -> CashAccountScreen(modifier)
                3 -> ArticlesScreen(modifier)
                4 -> SettingsScreen(modifier)
            }
        }
    }
}
