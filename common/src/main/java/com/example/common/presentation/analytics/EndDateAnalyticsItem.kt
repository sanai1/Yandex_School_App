package com.example.common.presentation.analytics

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem
import java.time.LocalDate

@Composable
fun EndDateAnalyticsItem(
    endDate: LocalDate,
    modifier: Modifier,
    updateDate: (String) -> Unit
) {
    ListItem(
        itemModelUI = ListItemModelUI(
            title = "Период: конец",
            info = endDate.toString().split("-").reversed().joinToString("."),
            typeListItem = TypeListItem.USUAL,
        ),
        modifier = modifier.height(56.dp),
        isAnalytics = true,
        onClickDate = { newEndDate ->
            updateDate.invoke(newEndDate)
        }
    )
}