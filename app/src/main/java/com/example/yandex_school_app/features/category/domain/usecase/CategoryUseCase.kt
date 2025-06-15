package com.example.yandex_school_app.features.category.domain.usecase

import com.example.yandex_school_app.features.category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val categoriesRepository: CategoryRepository
) {
    fun getCategories() = categoriesRepository.getCategories()
}