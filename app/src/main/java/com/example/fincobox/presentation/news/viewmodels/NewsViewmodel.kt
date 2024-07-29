package com.example.fincobox.presentation.news.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fincobox.domain.news.models.NewsData
import com.example.fincobox.domain.news.usecases.TopHeadlinesUseCase
import com.example.fincobox.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewmodel @Inject constructor(
    private val getTopHeadlinesUseCase: TopHeadlinesUseCase,
) : ViewModel() {

    private val _newsState: MutableLiveData<UiState<NewsData>> = MutableLiveData(UiState.Loading())
    val newsState: LiveData<UiState<NewsData>> = _newsState

    companion object {
        private const val TAG = "NewsViewmodel"
    }

    init {
        getTopHeadlines()
    }

    private fun getTopHeadlines() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getTopHeadlinesUseCase(1, 20)
            Log.i(TAG, "getTopHeadlines: $response")
            _newsState.postValue(response)
        }
    }

}