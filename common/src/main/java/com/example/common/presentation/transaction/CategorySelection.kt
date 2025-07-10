package com.example.common.presentation.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.domain.entity.category.CategoryDomain
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelection(
    modifier: Modifier,
    selectedCategory: CategoryDomain,
    categoryList: List<CategoryDomain>,
    updateCategory: (CategoryDomain) -> Unit
) {
    var selectedCategory by remember { mutableStateOf(selectedCategory) }
    var visibleBottomSheet by remember { mutableStateOf(false) }
    ListItem(
        itemModelUI = ListItemModelUI(
            title = "Статья",
            info = selectedCategory.name,
            typeListItem = TypeListItem.ARROW,
        ),
        modifier = modifier,
        onClickDetails = {
            visibleBottomSheet = true
        }
    )
    updateCategory.invoke(selectedCategory)
    if (visibleBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { visibleBottomSheet = false },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                categoryList.forEach { it ->
                    ListItem(
                        itemModelUI = ListItemModelUI(
                            title = it.name,
                            typeListItem = TypeListItem.USUAL,
                            payload = it.id.toString()
                        ),
                        modifier = modifier.height(70.dp),
                        onClickContainer = { item ->
                            categoryList.firstOrNull { it.id.toString() == item.payload }?.let {
                                selectedCategory = it
                                updateCategory.invoke(it)
                            }
                            visibleBottomSheet = false
                        }
                    )
                }
            }
        }
    }
}