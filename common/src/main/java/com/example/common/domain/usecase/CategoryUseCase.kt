package com.example.common.domain.usecase

import com.example.common.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val categoriesRepository: CategoryRepository
) {
    suspend fun getCategories() = categoriesRepository.getCategories()
    suspend fun getIncomeCategories() = categoriesRepository.getCategoriesByType(true)
    suspend fun getExpenseCategories() = categoriesRepository.getCategoriesByType(false)
}