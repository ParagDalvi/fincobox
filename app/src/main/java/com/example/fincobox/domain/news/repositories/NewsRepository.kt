package com.example.fincobox.domain.news.repositories

import com.example.fincobox.data.ApiResult
import com.example.fincobox.data.news.models.NewsResponseDto

interface NewsRepository {
    suspend fun getTopHeadlines(page: Int, pageSize: Int): NewsResponseDto

    suspend fun searchNews(query: String, page: Int, pageSize: Int): NewsResponseDto
}