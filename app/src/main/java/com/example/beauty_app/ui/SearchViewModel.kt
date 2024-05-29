package com.example.beauty_app.ui

import MakeupItem
import MakeupItemInteractor
import MakeupItemSearchResult
import SearchHistoryInteractor
import SearchState
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel(
    private val makeupInteractor: MakeupItemInteractor,
    private val searchHistoryInteractor: SearchHistoryInteractor) : ViewModel() {
    private var screenStateLiveData = MutableLiveData<SearchState>(SearchState.Default)
    private var searchData: String = SEARCH_DEF
    private var lastSearch: String = SEARCH_DEF
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
        MakeupSearch(searchData)
    }

    fun searchDebounce() {
        handler.removeCallbacks(searchRunnable, SEARCH_RUNNABLE_TOKEN)
        handler.postDelayed(
            searchRunnable,
            SEARCH_RUNNABLE_TOKEN,
            SEARCH_DEBOUNCE_DELAY
        )
    }

    fun setSearchData(input: String) {
        searchData = input
    }

    fun getSearchData() = searchData
    fun getScreenStateLiveData(): LiveData<SearchState> = screenStateLiveData

    fun writeToHistory(input: MakeupItem) {
        searchHistoryInteractor.write(input)
    }


    fun immediateSearch() {
        handler.removeCallbacks(searchRunnable, SEARCH_RUNNABLE_TOKEN)
        MakeupSearch(searchData)
    }

    fun searchLast() {
        handler.removeCallbacks(searchRunnable, SEARCH_RUNNABLE_TOKEN)
        MakeupSearch(lastSearch)
    }

    private fun MakeupSearch(query: String) {
        makeupInteractor.searchTracks(query, object : MakeupItemInteractor.MakeupItemConsumer {
            override fun consume(searchResult: MakeupItemSearchResult) {
                Log.i("request", query)
                Log.i("request", searchResult.type.toString())
                handler.post {
                    when (searchResult.type) {
                        SearchResultType.SUCCESS -> {
                            renderState(SearchState.Content(searchResult.makeupItems))
                        }

                        SearchResultType.EMPTY -> {
                            renderState(SearchState.EmptyResults)
                        }

                        SearchResultType.LOADING -> {
                            renderState(SearchState.Loading)
                        }

                        SearchResultType.ERROR -> {
                            renderState(SearchState.NetworkError)
                            lastSearch = query
                        }
                    }
                }

            }
        }
        )
    }

    private fun renderState(state: SearchState) {
        screenStateLiveData.postValue(state)
    }

    fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
    }

    companion object {
        const val SEARCH_RUNNABLE_TOKEN = 1
        const val SEARCH_DEF = ""
        const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}