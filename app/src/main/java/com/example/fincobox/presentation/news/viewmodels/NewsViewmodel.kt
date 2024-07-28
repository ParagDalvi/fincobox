package com.example.fincobox.presentation.news.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fincobox.domain.news.models.NewsData
import com.example.fincobox.domain.news.usecases.TopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewmodel @Inject constructor(
    private val getTopHeadlinesUseCase: TopHeadlinesUseCase,
) : ViewModel() {

    private val _newsState: MutableLiveData<NewsData> = MutableLiveData()
    val newsState: LiveData<NewsData> = _newsState

    companion object {
        private const val TAG = "NewsViewmodel"
    }

    init {
        getTopHeadlines()
    }

    private fun getTopHeadlines() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getTopHeadlinesUseCase(1, 20)
                Log.i(TAG, "getTopHeadlines: $response")
                _newsState.postValue(response)
            } catch (e: Exception) {
                Log.e(TAG, "getTopHeadlines: Failed", e)
            }
        }
    }

}