package com.example.fincobox.domain.news.usecases

import com.example.fincobox.domain.news.models.NewsData
import com.example.fincobox.domain.news.models.toNewsData
import com.example.fincobox.domain.news.repositories.NewsRepository
import javax.inject.Inject

class TopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int): NewsData {
        return newsRepository.getTopHeadlines(page, pageSize).toNewsData()
    }
}