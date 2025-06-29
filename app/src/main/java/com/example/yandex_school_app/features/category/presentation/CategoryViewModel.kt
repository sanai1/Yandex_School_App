package com.example.yandex_school_app.features.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.data.network.ResponseTemplate
import com.example.common.domain.entity.CategoryDomain
import com.example.category.domain.usecase.CategoryUseCase
import com.example.common.presentation.base_visible.VisibleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {
    private val _categories =
        MutableStateFlow<VisibleData<List<CategoryDomain>>>(VisibleData.Loading())
    val categories: StateFlow<VisibleData<List<CategoryDomain>>> = _categories.asStateFlow()

    fun updateCategory() = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            categoryUseCase.getCategories()
        }
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS ->
                _categories.value = VisibleData.Success(response.body!!.sortedBy { it.name })

            else -> _categories.value = VisibleData.Error(response.typeResponse)
        }
    }
}