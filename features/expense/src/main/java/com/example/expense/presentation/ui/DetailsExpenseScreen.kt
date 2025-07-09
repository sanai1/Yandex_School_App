package com.example.expense.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.expense.presentation.ExpenseViewModel

@Composable
fun DetailsExpenseScreen(
    modifier: Modifier,
    isExpenseClicked: MutableState<Boolean>,
    callback: () -> Unit,
    viewModel: ExpenseViewModel
) {

    LaunchedEffect(isExpenseClicked.value) {
        if (isExpenseClicked.value) {

        }
        callback.invoke()
    }

}