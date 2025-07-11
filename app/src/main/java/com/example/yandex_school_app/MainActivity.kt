package com.example.yandex_school_app

import android.os.Bundle
import androidx.compose.runtime.remember
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yandex_school_app.di.AppMain
import com.example.yandex_school_app.di.DaggerViewModelFactory
import com.example.cash_account.presentation.AccountViewModel
import com.example.category.presentation.CategoryViewModel
import com.example.common.store.ThemeStore
import com.example.expense.presentation.ExpenseViewModel
import com.example.income.presentation.IncomeViewModel
import com.example.yandex_school_app.ui.theme.Yandex_School_AppTheme
import javax.inject.Inject
import kotlin.reflect.KClass

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory
    val mapViewModel = mutableMapOf<KClass<out ViewModel>, ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AppMain).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        ThemeStore.init(this)
        enableEdgeToEdge()
        setContent {
            var isDark by remember { mutableStateOf(ThemeStore.isDarkTheme) }
            Yandex_School_AppTheme(
                darkTheme = isDark
            ) {
                var showSplash by remember { mutableStateOf(savedInstanceState == null) }
                mapViewModel[AccountViewModel::class] = viewModel<AccountViewModel>(
                    factory = viewModelFactory
                )
                mapViewModel[CategoryViewModel::class] = viewModel<CategoryViewModel>(
                    factory = viewModelFactory
                )
                mapViewModel[ExpenseViewModel::class] = viewModel<ExpenseViewModel>(
                    factory = viewModelFactory
                )
                mapViewModel[IncomeViewModel::class] = viewModel<IncomeViewModel>(
                    factory = viewModelFactory
                )
                if (showSplash) {
                    SplashScreen {
                        showSplash = false
                    }
                } else {
                    App { it ->
                        isDark = it
                        ThemeStore.isDarkTheme = it
                    }
                }
            }
        }
    }
}