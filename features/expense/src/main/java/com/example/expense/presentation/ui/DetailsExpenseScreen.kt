package com.example.expense.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.common.presentation.base_visible.ErrorVisible
import com.example.common.presentation.base_visible.LoadingVisible
import com.example.common.presentation.base_visible.VisibleData
import com.example.common.presentation.toast.ToastController
import com.example.common.presentation.transaction.AccountSelection
import com.example.common.presentation.transaction.AmountEntering
import com.example.common.presentation.transaction.CategorySelection
import com.example.common.presentation.transaction.CommentEntering
import com.example.common.presentation.transaction.DateSelection
import com.example.common.presentation.transaction.TimeSelection
import com.example.common.store.TransactionStore
import com.example.expense.presentation.ExpenseViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun DetailsExpenseScreen(
    modifier: Modifier,
    isExpenseClicked: MutableState<Boolean>,
    callback: () -> Unit,
    callbackNavController: () -> Unit,
    viewModel: ExpenseViewModel,
) {
    val transactionDomain: TransactionDomain? = TransactionStore.selectedTransaction.value
    var account by remember { mutableStateOf<AccountDomain?>(transactionDomain?.accountDomain) }
    var category by remember { mutableStateOf<CategoryDomain?>(transactionDomain?.categoryDomain) }
    var amount by remember { mutableStateOf(transactionDomain?.amount ?: "") }
    var date by remember {
        mutableStateOf(
            transactionDomain?.transactionDate?.toLocalDate() ?: LocalDate.now()
        )
    }
    var time by remember {
        mutableStateOf(
            transactionDomain?.transactionDate?.toLocalTime() ?: LocalTime.now()
        )
    }
    var comment by remember { mutableStateOf(transactionDomain?.comment ?: "") }
    LaunchedEffect(isExpenseClicked.value) {
        if (isExpenseClicked.value) {
            if (account == null) {
                ToastController.showToast("Выберите счет")
            } else if (category == null) {
                ToastController.showToast("Выберите категорию")
            } else if (amount.isEmpty() || amount.toDouble() == 0.0) {
                ToastController.showToast("Введите сумму транзакции")
            } else {
                if (transactionDomain == null) {
                    viewModel.createTransaction(
                        TransactionPartDomain(
                            accountId = account!!.id,
                            categoryId = category!!.id,
                            amount = amount,
                            transactionDate = LocalDateTime.of(date, time),
                            comment = comment
                        )
                    )
                } else {
                    viewModel.updateTransaction(
                        transactionId = transactionDomain.id,
                        transactionPartDomain = TransactionPartDomain(
                            accountId = account!!.id,
                            categoryId = category!!.id,
                            amount = amount,
                            transactionDate = LocalDateTime.of(date, time),
                            comment = comment,
                        )
                    )
                }
                callbackNavController.invoke()
            }
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
                selectedAccount = account
                    ?: (accountsAll.value as VisibleData.Success<List<AccountDomain>>).data.first(),
                accountList = (accountsAll.value as VisibleData.Success<List<AccountDomain>>).data
            ) { newAccount ->
                account = newAccount
            }
            CategorySelection(
                modifier = modifier.height(70.dp),
                selectedCategory = category
                    ?: (categoryExpense.value as VisibleData.Success<List<CategoryDomain>>).data.first(),
                categoryList = (categoryExpense.value as VisibleData.Success<List<CategoryDomain>>).data
            ) { newCategory ->
                category = newCategory
            }
            AmountEntering(
                modifier = modifier.height(70.dp),
                enterAmount = amount.let { it.ifEmpty { "0" } },
                currency = viewModel.getSelectedAccount().value.currency
            ) { newAmount ->
                amount = newAmount
            }
            DateSelection(
                modifier = modifier.height(70.dp),
                date = date
            ) { newDate ->
                return@DateSelection if (newDate.isAfter(LocalDate.now())) {
                    ToastController.showToast("Выберите прошедшую дату")
                    false
                } else {
                    date = newDate
                    true
                }
            }
            TimeSelection(
                modifier = modifier.height(70.dp),
                time = time
            ) { newTime ->
                return@TimeSelection if (date == LocalDate.now() && newTime.isAfter(LocalTime.now())) {
                    ToastController.showToast("Выберите прошедшее время")
                    false
                } else {
                    time = newTime
                    true
                }
            }
            CommentEntering(
                modifier = modifier.height(70.dp),
                enterComment = comment
            ) { newComment ->
                comment = newComment
            }
            if (transactionDomain != null) {
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = {
                        viewModel.deleteTransactionById(transactionDomain.id)
                        callbackNavController.invoke()
                    }) {
                    Text("Удалить расход")
                }
            }
        }
    } else {
        ErrorVisible(if (categoryExpense.value is VisibleData.Error) (categoryExpense.value as VisibleData.Error).type else (accountsAll.value as VisibleData.Error).type)
    }
}