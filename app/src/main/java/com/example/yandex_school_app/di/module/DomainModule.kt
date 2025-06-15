package com.example.yandex_school_app.di.module

import com.example.yandex_school_app.common.data.repository.TransactionRepositoryImpl
import com.example.yandex_school_app.common.domain.repository.TransactionRepository
import com.example.yandex_school_app.di.ApplicationScope
import com.example.yandex_school_app.features.cash_account.data.repository.AccountRepositoryImpl
import com.example.yandex_school_app.features.cash_account.domain.repository.AccountRepository
import com.example.yandex_school_app.features.category.data.repository.CategoryRepositoryImpl
import com.example.yandex_school_app.features.category.domain.repository.CategoryRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @ApplicationScope
    @Binds
    fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @ApplicationScope
    @Binds
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @ApplicationScope
    @Binds
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}