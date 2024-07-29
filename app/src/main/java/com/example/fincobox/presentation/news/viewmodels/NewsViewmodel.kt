package com.example.fincobox.presentation.news.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.fincobox.domain.news.models.Article
import com.example.fincobox.domain.news.usecases.SearchUseCase
import com.example.fincobox.domain.news.usecases.TopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewmodel @Inject constructor(
    private val getTopHeadlinesUseCase: TopHeadlinesUseCase,
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    private val _topArticles = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val topArticles = _topArticles.asStateFlow()

    private val _searchedArticles = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val searchedArticles = _searchedArticles.asStateFlow()

    private val _hasUserSearched = MutableStateFlow(false)
    val hasUserSearched = _hasUserSearched.asStateFlow()

    companion object {
        private const val TAG = "NewsViewmodel"
    }

    init {
        getTopHeadlines()
    }

    private fun getTopHeadlines() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getTopHeadlinesUseCase()
            response.cachedIn(viewModelScope).collect {
                _topArticles.value = it
            }
        }
    }

    fun performSearch(query: String) {
        _hasUserSearched.value = query.isNotBlank()
        _searchedArticles.value = PagingData.empty()
        viewModelScope.launch(Dispatchers.IO) {
            val response = searchUseCase(query)
            response.cachedIn(viewModelScope).collect {
                _searchedArticles.value = it
            }
        }
    }

}