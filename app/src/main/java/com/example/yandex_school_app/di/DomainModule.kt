package com.example.yandex_school_app.di

import com.example.yandex_school_app.features.category.data.repository.CategoryRepositoryImpl
import com.example.yandex_school_app.features.category.domain.repository.CategoryRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @ApplicationScope
    @Binds
    fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository
}