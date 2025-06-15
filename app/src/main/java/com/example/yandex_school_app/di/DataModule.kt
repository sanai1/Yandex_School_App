package com.example.yandex_school_app.di

import com.example.yandex_school_app.features.category.data.datasource.remote.CategoryRemoteDataSource
import com.example.yandex_school_app.features.category.data.datasource.remote.impl.CategoryRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindCategoryRemoteDataSource(impl: CategoryRemoteDataSourceImpl): CategoryRemoteDataSource
}