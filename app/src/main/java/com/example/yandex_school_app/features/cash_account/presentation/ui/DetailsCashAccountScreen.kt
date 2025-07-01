package com.example.yandex_school_app.features.cash_account.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yandex_school_app.MainActivity
import com.example.yandex_school_app.features.cash_account.presentation.AccountViewModel

@Composable
fun DetailsCashAccountScreen(
    navController: NavController,
    modifier: Modifier,
    isUpdateAccountClicked: MutableState<Boolean>,
    callback: () -> Unit,
    viewModel: AccountViewModel = viewModel(
        factory = (LocalContext.current as MainActivity).viewModelFactory
    )
) {
    LaunchedEffect(isUpdateAccountClicked.value) {
        if (isUpdateAccountClicked.value) {
            navController.popBackStack()
        }
        callback.invoke()
    }
}