package com.example.beauty_app.ui

import MakeupItem
import SearchHistoryInteractor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchHistoryViewModel( private val searchHistoryInteractor: SearchHistoryInteractor):ViewModel() {
    private var screenStateLiveData = MutableLiveData<SearchHistoryState>(SearchHistoryState.Default)
    fun writeToHistory(input: MakeupItem) {
        searchHistoryInteractor.write(input)
    }
    fun getScreenStateLiveData(): LiveData<SearchHistoryState> = screenStateLiveData
    fun showHistory(){
        val history = searchHistoryInteractor.read()
        if(history.isNotEmpty()){
            renderState(SearchHistoryState.History(history))
        }else{
            renderState(SearchHistoryState.EmptyHistory)
        }
    }
    fun clearHistory(){
        searchHistoryInteractor.clear()
        renderState(SearchHistoryState.EmptyHistory)
    }
    private fun renderState(state: SearchHistoryState){
        screenStateLiveData.postValue(state)
    }

}