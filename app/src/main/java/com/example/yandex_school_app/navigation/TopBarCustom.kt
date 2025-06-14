package com.example.yandex_school_app.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yandex_school_app.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCustom(
    navController: NavController,
    selectedItem: NavigationCustomItem,
    modifier: Modifier = Modifier
) {
    @Composable
    fun TextOnTopBar(text: String, modifier: Modifier = Modifier) {
        Text(text, modifier = modifier, textAlign = TextAlign.Center)
    }

    @Composable
    fun IconButtonOnTopBar(icon: Int, onClick: () -> Unit) {
        IconButton(onClick = onClick) {
            Icon(painter = painterResource(icon), contentDescription = "")
        }
    }

    @Composable
    fun IconButtonOnTopBar(icon: ImageVector, onClick: () -> Unit) {
        IconButton(onClick = onClick) {
            Icon(icon, contentDescription = "")
        }
    }
    TopAppBar(
        title = {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (selectedItem) {
                    is NavigationCustomItem.Expense -> {
                        Spacer(modifier = modifier.width(25.dp))
                        TextOnTopBar("Расходы сегодня", modifier.weight(1f))
                        IconButtonOnTopBar(R.drawable.history) { navController.navigate(ScreenName.HISTORY_EXPENSE) }
                    }

                    is NavigationCustomItem.Income -> {
                        Spacer(modifier = modifier.width(25.dp))
                        TextOnTopBar("Доходы сегодня", modifier.weight(1f))
                        IconButtonOnTopBar(R.drawable.history) { navController.navigate(ScreenName.HISTORY_INCOME) }
                    }

                    is NavigationCustomItem.CashAccount -> {
                        Spacer(modifier = modifier.width(25.dp))
                        TextOnTopBar("Мой счет", modifier.weight(1f))
                        IconButtonOnTopBar(Icons.Default.Create) { }
                    }

                    is NavigationCustomItem.Category -> TextOnTopBar(
                        "Мои статьи",
                        modifier.weight(1f)
                    )

                    is NavigationCustomItem.Settings -> TextOnTopBar(
                        "Настройки",
                        modifier.weight(1f)
                    )

                    is NavigationCustomItem.HistoryExpense -> {
                        IconButtonOnTopBar(Icons.Default.ArrowBack) { navController.popBackStack() }
                        TextOnTopBar("Моя история", modifier.weight(1f))
                        IconButtonOnTopBar(R.drawable.analitics) { }
                    }

                    is NavigationCustomItem.HistoryIncome -> {
                        IconButtonOnTopBar(Icons.Default.ArrowBack) { navController.popBackStack() }
                        TextOnTopBar("Моя история", modifier.weight(1f))
                        IconButtonOnTopBar(R.drawable.analitics) { }
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}