package com.example.category.domain.usecase

import com.example.category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val categoriesRepository: CategoryRepository
) {
    suspend fun getCategories() = categoriesRepository.getCategories()
}