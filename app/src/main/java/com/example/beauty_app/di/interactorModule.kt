package com.example.beauty_app.di

import MakeupItemInteractor
import MakeupItemInteractorImpl
import SearchHistoryInteractor
import SearchHistoryInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    factory<SearchHistoryInteractor>{
        SearchHistoryInteractorImpl(get())
    }
    factory<MakeupItemInteractor>{
        MakeupItemInteractorImpl(get())
    }
}