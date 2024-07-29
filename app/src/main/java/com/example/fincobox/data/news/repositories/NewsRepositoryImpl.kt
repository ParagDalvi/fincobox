package com.example.fincobox.data.news.repositories

import com.example.fincobox.BuildConfig
import com.example.fincobox.data.news.NewsApi
import com.example.fincobox.data.news.models.NewsResponseDto
import com.example.fincobox.domain.news.repositories.NewsRepository
import com.example.fincobox.util.COUNTRY_CODE
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {

    override suspend fun getTopHeadlines(page: Int, pageSize: Int): NewsResponseDto {
        return newsApi.getTopHeadlines(COUNTRY_CODE, page, pageSize, BuildConfig.NEWS_API_KEY)
    }

}