package com.example.fincobox.data.news.repositories

import com.example.fincobox.data.ApiResult
import com.example.fincobox.data.apiCall
import com.example.fincobox.data.news.NewsApi
import com.example.fincobox.data.news.models.NewsResponseDto
import com.example.fincobox.domain.news.repositories.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {

    override suspend fun getTopHeadlines(page: Int, pageSize: Int): ApiResult<NewsResponseDto> {
        return apiCall {
            newsApi.getTopHeadlines("us", page, pageSize, "aa4130e9df3a4ac6adb48e4f78d26edf")
        }
    }

}