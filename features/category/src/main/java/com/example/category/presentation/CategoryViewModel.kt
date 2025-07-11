package com.example.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.ResponseTemplate
import com.example.common.domain.entity.category.CategoryDomain
import com.example.common.domain.usecase.CategoryUseCase
import com.example.common.presentation.base_visible.VisibleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {
    private val _categories =
        MutableStateFlow<VisibleData<List<CategoryDomain>>>(VisibleData.Loading())
    val categories: StateFlow<VisibleData<List<CategoryDomain>>> = _categories.asStateFlow()

    @Volatile
    private var allCategories = listOf<CategoryDomain>()

    fun updateCategory() = viewModelScope.launch(Dispatchers.IO) {
        val response = categoryUseCase.getCategories()
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> {
                response.body?.let { it ->
                    VisibleData.Success(it.sortedBy { it.name }).let {
                        _categories.value = it
                        allCategories = it.data
                    }
                }
            }

            else -> _categories.value = VisibleData.Error(response.typeResponse)
        }
    }

    fun searchCategory(query: String) = viewModelScope.launch(Dispatchers.IO) {
        if ((_categories.value is VisibleData.Success).not()) {
            return@launch
        }
        _categories.value = VisibleData.Success(
            allCategories.filter {
                it.name.lowercase().contains(query.lowercase())
            }.sortedWith(
                compareBy {
                    !it.name.lowercase().startsWith(query.lowercase())
                }
            )
        )
    }
}