package com.example.beauty_app.di

import LocalMakeupStorageHandler
import MakeupItemRepository
import MakeupItemRepositoryImpl
import SearchHistoryRepository
import SearchHistoryRepositoryImpl
import SharedPreferencesLocalMakeupStorageHandler
import org.koin.dsl.module

val repositoryModule = module{

    single<SearchHistoryRepository>{
        SearchHistoryRepositoryImpl(get())
    }
    single<MakeupItemRepository>{
        MakeupItemRepositoryImpl(get())
    }
    single<LocalMakeupStorageHandler> {
        SharedPreferencesLocalMakeupStorageHandler(get(),get())
    }
}