package com.example.beauty_app.di

import com.example.beauty_app.ui.SearchHistoryViewModel
import com.example.beauty_app.ui.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<SearchViewModel> {
        SearchViewModel(get(), get())
    }
    viewModel {
        SearchHistoryViewModel(get())
    }
}