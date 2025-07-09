package com.example.income.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.income.presentation.viewmodel.IncomeViewModel

@Composable
fun DetailsIncomeScreen(
    modifier: Modifier,
    isIncomeClicked: MutableState<Boolean>,
    callback: () -> Unit,
    viewModel: IncomeViewModel
) {

    LaunchedEffect(isIncomeClicked.value) {
        if (isIncomeClicked.value) {

        }
        callback.invoke()
    }

}