package com.example.common.presentation.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.domain.entity.account.Currency
import com.example.common.domain.entity.transaction.TransactionDomain
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem

@Composable
fun ListAnalyticsTransaction(
    transactions: List<TransactionDomain>,
    currency: Currency,
    modifier: Modifier,
    getPartForTransaction: (String) -> String,
    onClickDetails: (TransactionDomain) -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        transactions.forEach { item ->
            ListItem(
                itemModelUI = ListItemModelUI(
                    picture = item.categoryDomain.emoji,
                    title = item.categoryDomain.name,
                    description = item.comment,
                    info = "${getPartForTransaction.invoke(item.amount)}%",
                    infoDescription = "${item.amount} ${currency.symbol}",
                    typeListItem = TypeListItem.ARROW
                ),
                modifier = modifier.height(70.dp),
                onClickDetails = {
                    onClickDetails.invoke(item)
                }
            )
        }
    }
}