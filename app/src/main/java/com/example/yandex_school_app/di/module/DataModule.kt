package com.example.yandex_school_app.di.module

import com.example.common.data.datasource.remote.TransactionRemoteDataSource
import com.example.common.data.datasource.remote.impl.TransactionRemoteDataSourceImpl
import com.example.network.di.ApplicationScope
import com.example.cash_account.data.datasource.remote.AccountRemoteDataSource
import com.example.cash_account.data.datasource.remote.impl.AccountRemoteDataSourceImpl
import com.example.category.data.datasource.remote.CategoryRemoteDataSource
import com.example.category.data.datasource.remote.impl.CategoryRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindCategoryRemoteDataSource(impl: CategoryRemoteDataSourceImpl): CategoryRemoteDataSource

    @ApplicationScope
    @Binds
    fun bindTransactionRemoteDataSource(impl: TransactionRemoteDataSourceImpl): TransactionRemoteDataSource

    @ApplicationScope
    @Binds
    fun bindAccountRemoteDataSource(impl: AccountRemoteDataSourceImpl): AccountRemoteDataSource
}