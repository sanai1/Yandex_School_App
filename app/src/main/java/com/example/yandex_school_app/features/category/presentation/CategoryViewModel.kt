package com.example.yandex_school_app.features.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandex_school_app.common.data.network.ResponseTemplate
import com.example.yandex_school_app.common.domain.entity.CategoryDomain
import com.example.yandex_school_app.common.presentation.ToastController
import com.example.yandex_school_app.features.category.domain.usecase.CategoryUseCase
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
    private val _categories = MutableStateFlow<List<CategoryDomain>>(emptyList())
    val categories: StateFlow<List<CategoryDomain>> = _categories.asStateFlow()

    fun updateCategory() = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            categoryUseCase.getCategories()
        }
        when (response.typeResponse) {
            ResponseTemplate.TypeResponse.SUCCESS -> _categories.value =
                response.body!!.sortedBy { it.name }

            ResponseTemplate.TypeResponse.UNAUTHORIZED -> ToastController.showToast("Ошибка авторизации")
            ResponseTemplate.TypeResponse.ERROR_SERVER -> ToastController.showToast("Ошибка сервера")
            else -> ToastController.showToast("Неизвестная ошибка")
        }
    }
}