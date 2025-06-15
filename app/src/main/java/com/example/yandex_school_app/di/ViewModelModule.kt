package com.example.yandex_school_app.di

import androidx.lifecycle.ViewModel
import com.example.yandex_school_app.features.category.presentation.CategoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    @Binds
    fun bindCategoryViewModel(impl: CategoryViewModel): ViewModel
}