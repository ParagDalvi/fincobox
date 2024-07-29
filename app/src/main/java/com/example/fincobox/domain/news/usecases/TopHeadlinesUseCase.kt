package com.example.fincobox.domain.news.usecases

import com.example.fincobox.data.toUiStates
import com.example.fincobox.domain.news.models.NewsData
import com.example.fincobox.domain.news.models.toNewsData
import com.example.fincobox.domain.news.repositories.NewsRepository
import com.example.fincobox.presentation.UiState
import javax.inject.Inject

class TopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int): UiState<NewsData> {
        return newsRepository.getTopHeadlines(page, pageSize).toUiStates {
            it.toNewsData()
        }
    }
}