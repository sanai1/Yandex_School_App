package com.example.yandex_school_app

import android.content.SharedPreferences
import android.os.Bundle
import androidx.compose.runtime.remember
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.yandex_school_app.di.AppMain
import com.example.yandex_school_app.di.DaggerViewModelFactory
import com.example.cash_account.presentation.AccountViewModel
import com.example.category.presentation.CategoryViewModel
import com.example.common.store.AppTheme
import com.example.common.store.NamedStore
import com.example.expense.presentation.ExpenseViewModel
import com.example.income.presentation.IncomeViewModel
import com.example.settings.presentation.PinManager
import com.example.settings.presentation.SettingsViewmodel
import com.example.yandex_school_app.ui.theme.Yandex_School_AppTheme
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.reflect.KClass

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var workManager: WorkManager
    val mapViewModel = mutableMapOf<KClass<out ViewModel>, ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AppMain).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        val isFirst = sharedPreferences.getBoolean(NamedStore.IS_FIRST_RUN, true)
        if (isFirst) {
            val constraints =
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val workRequest =
                PeriodicWorkRequestBuilder<SyncWorker>(2, TimeUnit.HOURS).setConstraints(
                    constraints
                ).build()
            workManager.enqueue(workRequest)
        }
        enableEdgeToEdge()
        setContent {
            var isDark by remember {
                mutableStateOf(
                    sharedPreferences.getBoolean(
                        NamedStore.DARK_THEME,
                        false
                    )
                )
            }
            var primaryColorVariant by remember {
                mutableStateOf(
                    AppTheme.PrimaryColorVariant.valueOf(
                        sharedPreferences.getString(
                            NamedStore.PRIMARY_COLOR,
                            "GREEN"
                        ) ?: "GREEN"
                    )
                )
            }
            Yandex_School_AppTheme(
                darkTheme = isDark,
                primaryColorVariant = primaryColorVariant
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
                mapViewModel[SettingsViewmodel::class] = viewModel<SettingsViewmodel>(
                    factory = viewModelFactory
                )
                if (showSplash) {
                    SplashScreen {
                        showSplash = false
                    }
                } else {
                    var showApp by remember { mutableStateOf(false) }
                    if (PinManager(LocalContext.current).pinIsSet()) {
                        EnterPinScreen {
                            showApp = true
                        }
                    } else {
                        showApp = true
                    }
                    if (showApp) {
                        App(
                            onChangeTheme = { isDarkNew ->
                                isDark = isDarkNew
                            },
                            onChangePrimaryColor = { variant ->
                                primaryColorVariant = variant
                            }
                        )
                    }
                }
            }
        }
    }
}