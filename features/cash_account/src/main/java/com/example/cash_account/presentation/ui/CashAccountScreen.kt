package com.example.cash_account.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bar.BarChart
import com.example.bar.BarChartData
import com.example.common.domain.entity.account.Currency
import com.example.cash_account.R
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem
import com.example.cash_account.presentation.AccountViewModel
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.presentation.base_visible.ErrorVisible
import com.example.common.presentation.base_visible.LoadingVisible
import com.example.common.presentation.base_visible.VisibleData
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashAccountScreen(
    modifier: Modifier, viewModel: AccountViewModel
) {
    val selectedAccount = viewModel.getSelectedAccount().collectAsStateWithLifecycle()
    val accounts by viewModel.allAccount.collectAsStateWithLifecycle()
    if (accounts.isEmpty()) {
        viewModel.updateAllAccount()
    }
    val transaction by viewModel.transactions.collectAsStateWithLifecycle()
    if (transaction is VisibleData.Loading) {
        viewModel.updateTransactions()
    }
    Column {
        var visibleBottomSheet by remember { mutableStateOf(TypeModalBottomSheet.NONE) }
        ListItem(
            itemModelUI = ListItemModelUI(
                picture = "\uD83D\uDCB0",
                title = selectedAccount.value.name,
                info = selectedAccount.value.let {
                    "${it.balance} ${it.currency.symbol}"
                },
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier
                .height(56.dp)
                .background(MaterialTheme.colorScheme.secondary),
            onClickDetails = {
                visibleBottomSheet = TypeModalBottomSheet.ACCOUNT
            }
        )
        ListItem(
            itemModelUI = ListItemModelUI(
                title = "Валюта",
                info = selectedAccount.value.currency.symbol,
                typeListItem = TypeListItem.ARROW
            ),
            modifier = modifier
                .height(56.dp)
                .background(MaterialTheme.colorScheme.secondary),
            onClickDetails = {
                visibleBottomSheet = TypeModalBottomSheet.CURRENCY
            }
        )
        when (transaction) {
            is VisibleData.Loading<*> -> LoadingVisible()
            is VisibleData.Success<*> -> BarChart(
                data = (transaction as VisibleData.Success<List<TransactionDomain>>).data
                    .groupBy { it.transactionDate.toLocalDate() }
                    .let { groupedTransactions ->
                        val today = LocalDate.now()
                        val last30Days =
                            (0..29).map { today.minusDays(it.toLong()) }.sortedBy { it }
                        last30Days.map { date ->
                            groupedTransactions[date]?.let { transactions ->
                                BarChartData(
                                    value = transactions.sumOf { transaction ->
                                        transaction.amount.toDouble()
                                    }.toFloat(),
                                    isIncome = transactions[0].categoryDomain.isIncome
                                )
                            } ?: BarChartData(
                                value = 1f,
                                isIncome = true
                            )
                        }
                    }
            )

            is VisibleData.Error<*> -> ErrorVisible((transaction as VisibleData.Error<*>).type)
        }
        if (visibleBottomSheet != TypeModalBottomSheet.NONE) {
            val sheetState = rememberModalBottomSheetState()
            ModalBottomSheet(
                onDismissRequest = { visibleBottomSheet = TypeModalBottomSheet.NONE },
                sheetState = sheetState
            ) {
                if (visibleBottomSheet == TypeModalBottomSheet.ACCOUNT) {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        accounts.forEach { account ->
                            ListItem(
                                itemModelUI = ListItemModelUI(
                                    title = account.name,
                                    info = "${account.balance} ${account.currency.symbol}",
                                    typeListItem = TypeListItem.USUAL,
                                    payload = account.id.toString()
                                ),
                                modifier = modifier.height(70.dp),
                                onClickContainer = {
                                    viewModel.setSelectedAccountById(it.payload)
                                    visibleBottomSheet = TypeModalBottomSheet.NONE
                                }
                            )
                        }
                    }
                } else {
                    Column {
                        Currency.collectionCurrency.forEach { currency ->
                            ListItem(
                                itemModelUI = ListItemModelUI(
                                    icon = currency.icon,
                                    title = currency.name,
                                    typeListItem = TypeListItem.USUAL
                                ),
                                modifier = modifier.height(70.dp),
                                onClickContainer = { item ->
                                    viewModel.updateCurrencyOnSelectedAccount(Currency.collectionCurrency.find { it.name == item.title }
                                        ?: Currency.collectionCurrency.first())
                                    visibleBottomSheet = TypeModalBottomSheet.NONE
                                }
                            )
                        }
                        ListItem(
                            itemModelUI = ListItemModelUI(
                                icon = R.drawable.back,
                                title = "Отмена",
                                typeListItem = TypeListItem.USUAL,
                            ),
                            modifier = modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .height(70.dp),
                            onClickContainer = {
                                visibleBottomSheet = TypeModalBottomSheet.NONE
                            },
                        )
                    }
                }
            }
        }
    }
}

enum class TypeModalBottomSheet {
    NONE,
    ACCOUNT,
    CURRENCY
}