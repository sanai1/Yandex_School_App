package com.example.yandex_school_app.features.category.presentation

import SearchBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.domain.entity.CategoryDomain
import com.example.common.domain.entity.ListItemModelUI
import com.example.common.presentation.base_visible.ErrorVisible
import com.example.common.presentation.base_visible.LoadingVisible
import com.example.common.presentation.base_visible.VisibleData
import com.example.common.presentation.list.ListItem
import com.example.common.presentation.list.TypeListItem

@Composable
fun CategoryScreen(
    modifier: Modifier,
    viewModel: CategoryViewModel
) {
    val categories = viewModel.categories.collectAsStateWithLifecycle()
    when (categories.value) {
        is VisibleData.Loading -> {
            LoadingVisible(modifier)
            viewModel.updateCategory()
        }

        is VisibleData.Success -> Column {
            SearchBar {
                viewModel.searchCategory(it)
            }
            Column(
                modifier = modifier.verticalScroll(rememberScrollState())
            ) {
                (categories.value as VisibleData.Success<List<CategoryDomain>>).data.forEach { item ->
                    ListItem(
                        itemModelUI = ListItemModelUI(
                            picture = item.emoji,
                            title = item.name,
                            description = null,
                            info = null,
                            typeListItem = TypeListItem.USUAL
                        ),
                        modifier = modifier.height(70.dp),
                    )
                }
            }
        }

        is VisibleData.Error -> ErrorVisible((categories.value as VisibleData.Error<List<CategoryDomain>>).type)
    }
}