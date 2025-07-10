package com.example.expense.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.domain.entity.account.AccountDomain
import com.example.common.domain.entity.category.CategoryDomain
import com.example.common.presentation.base_visible.ErrorVisible
import com.example.common.presentation.base_visible.LoadingVisible
import com.example.common.presentation.base_visible.VisibleData
import com.example.common.presentation.toast.ToastController
import com.example.common.presentation.transaction.AccountSelection
import com.example.common.presentation.transaction.AmountEntering
import com.example.common.presentation.transaction.CategorySelection
import com.example.common.presentation.transaction.DateSelection
import com.example.expense.presentation.ExpenseViewModel
import java.time.LocalDate

@Composable
fun DetailsExpenseScreen(
    modifier: Modifier,
    isExpenseClicked: MutableState<Boolean>,
    callback: () -> Unit,
    viewModel: ExpenseViewModel,
) {
    var account by remember { mutableStateOf<AccountDomain?>(null) }
    var category by remember { mutableStateOf<CategoryDomain?>(null) }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now()) }
    var time by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    LaunchedEffect(isExpenseClicked.value) {
        if (isExpenseClicked.value) {
            if (account == null) {
                ToastController.showToast("Выберите счет")
            } else if (category == null) {
                ToastController.showToast("Выберите категорию")
            } else if (amount.isEmpty() || amount.toDouble() == 0.0) {
                ToastController.showToast("Введите сумму транзакции")
            }
//            viewModel.createTransaction(
//                TransactionPartDomain(
//                    accountId = viewModel.getSelectedAccount().value.id,
//                    categoryId = category!!.id,
//                    amount = amount,
//                    transactionDate = TODO(),
//                    comment = comment
//                )
//            )
        }
        callback.invoke()
    }
    val categoryExpense = viewModel.categoryExpense.collectAsStateWithLifecycle()
    viewModel.updateCategoryExpense()
    val accountsAll = viewModel.accountList.collectAsStateWithLifecycle()
    viewModel.updateAccounts()
    if (categoryExpense.value is VisibleData.Loading || accountsAll.value is VisibleData.Loading) {
        LoadingVisible()
    } else if (categoryExpense.value is VisibleData.Success && accountsAll.value is VisibleData.Success) {
        Column {
            AccountSelection(
                modifier = modifier.height(70.dp),
                accountList = (accountsAll.value as VisibleData.Success<List<AccountDomain>>).data
            ) { item ->
                account = item
            }
            CategorySelection(
                modifier = modifier.height(70.dp),
                categoryList = (categoryExpense.value as VisibleData.Success<List<CategoryDomain>>).data
            ) { item ->
                category = item
            }
            AmountEntering(
                modifier = modifier.height(70.dp),
                currency = viewModel.getSelectedAccount().value.currency
            ) { item ->
                amount = item
            }
            DateSelection(
                modifier = modifier.height(70.dp),
                date = date
            ) { item ->
                if (item.isAfter(LocalDate.now())) {
                    ToastController.showToast("Выберите прошедшую дату")
                } else {
                    date = item
                }
                println(date)
            }
        }
    } else {
        ErrorVisible((categoryExpense.value as VisibleData.Error).type)
    }
}