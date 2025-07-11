package com.example.yandex_school_app.di.module

import com.example.common.data.repository.TransactionRepositoryImpl
import com.example.common.domain.repository.TransactionRepository
import com.example.yandex_school_app.di.ApplicationScope
import com.example.cash_account.data.repository.AccountRepositoryImpl
import com.example.common.domain.repository.AccountRepository
import com.example.category.data.repository.CategoryRepositoryImpl
import com.example.common.domain.repository.CategoryRepository
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