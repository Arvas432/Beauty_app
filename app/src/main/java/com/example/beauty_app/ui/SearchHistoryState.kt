package com.example.beauty_app.ui

import MakeupItem

sealed class SearchHistoryState {
    object Default: SearchHistoryState()
    object EmptyHistory: SearchHistoryState()
    data class History(val products: List<MakeupItem>): SearchHistoryState()
}