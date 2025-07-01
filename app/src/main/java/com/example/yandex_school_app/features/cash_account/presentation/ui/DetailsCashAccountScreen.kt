package com.example.yandex_school_app.features.cash_account.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.domain.entity.AccountDomain
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem
import com.example.yandex_school_app.MainActivity
import com.example.yandex_school_app.features.cash_account.presentation.AccountViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailsCashAccountScreen(
    modifier: Modifier,
    viewModel: AccountViewModel = viewModel(
        factory = (LocalContext.current as MainActivity).viewModelFactory
    )
) {
    val accounts = viewModel.allAccount.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        accounts.value.forEach { account ->
            SwipeActionsExample(
                account,
                modifier,
                onUpdateAccount = {
                    viewModel.updateNameAndBalanceAccount(it)
                },
                onSwipeAccount = {
                    viewModel.deleteCashAccount(account.id)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeActionsExample(
    accountDomain: AccountDomain,
    modifier: Modifier = Modifier,
    onUpdateAccount: (AccountDomain) -> Unit,
    onSwipeAccount: () -> Unit
) {
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                showConfirmationDialog = true
            }
            false
        }
    )
    if (showConfirmationDialog) {
        ConfirmationDeleteDialog(
            onConfirm = {
                onSwipeAccount.invoke()
                showConfirmationDialog = false
            },
            onDismiss = {
                showConfirmationDialog = false
                coroutineScope.launch {
                    state.reset()
                }
            }
        )
    }
    if (showUpdateDialog) {
        UpdateNameAndBalanceAccountDialog(
            accountDomain,
            onConfirm = {
                onUpdateAccount.invoke(it)
                showUpdateDialog = false
            }, onDismiss = {
                showUpdateDialog = false
            }
        )
    }
    SwipeToDismissBox(
        state = state,
        backgroundContent = {
            val color = when {
                showConfirmationDialog || state.dismissDirection == SwipeToDismissBoxValue.EndToStart -> Color.LightGray
                else -> Color.Transparent
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(16.dp),
                contentAlignment = when (state.dismissDirection) {
                    SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                    else -> Alignment.Center
                }
            ) {
                when (state.dismissDirection) {
                    SwipeToDismissBoxValue.EndToStart -> Icon(Icons.Default.Delete, "Delete")
                    else -> Unit
                }
            }
        },
        content = {
            ListItem(
                itemModelUI = ListItemModelUI(
                    title = accountDomain.name,
                    info = "${accountDomain.balance} ${accountDomain.currency.symbol}",
                    typeListItem = TypeListItem.USUAL,
                ),
                modifier = modifier.height(56.dp),
                onClickContainer = {
                    showUpdateDialog = true
                }
            )
        }
    )
}