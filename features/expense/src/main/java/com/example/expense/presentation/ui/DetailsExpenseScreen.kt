package com.example.expense.presentation.ui

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.common.domain.entity.category.CategoryDomain
import com.example.common.domain.entity.transaction.TransactionPartDomain
import com.example.common.presentation.toast.ToastController
import com.example.expense.presentation.ExpenseViewModel

@Composable
fun DetailsExpenseScreen(
    modifier: Modifier,
    isExpenseClicked: MutableState<Boolean>,
    callback: () -> Unit,
    viewModel: ExpenseViewModel,
) {
    var amount by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var category by remember { mutableStateOf<CategoryDomain?>(null) }
    LaunchedEffect(isExpenseClicked.value) {
        if (isExpenseClicked.value) {
            if (amount.isEmpty()) {
                ToastController.showToast("Введите сумму транзакции")
            } else if (category == null) {
                ToastController.showToast("Выберите категорию")
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
}