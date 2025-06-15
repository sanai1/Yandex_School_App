package com.example.yandex_school_app.features.category.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yandex_school_app.features.category.domain.entity.CategoryDomain
import com.example.yandex_school_app.common.domain.ListItemModelUI
import com.example.yandex_school_app.common.presentation.ListItem
import com.example.yandex_school_app.common.presentation.TypeListItem

@Composable
fun CategoryScreen(listCategory: List<CategoryDomain>, modifier: Modifier) {
    val categories = remember { mutableStateOf(listCategory) }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(Color.LightGray)
                .padding(horizontal = 20.dp, vertical = 15.dp)
        ) {
            Text(
                "Найти статью", style = TextStyle(
                    fontSize = 17.sp
                )
            )
            Spacer(modifier = modifier.weight(1f))
            Icon(Icons.Default.Search, contentDescription = "")
        }
        categories.value.forEachIndexed { index, item ->
            ListItem(
                itemModelUI = ListItemModelUI(
                    picture = item.emoji,
                    title = item.name,
                    description = null,
                    info = null,
                    typeListItem = TypeListItem.USUAL
                ),
                modifier = modifier,
            )
        }
    }
}