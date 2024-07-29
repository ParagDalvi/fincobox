package com.example.fincobox.data.news

import com.example.fincobox.BuildConfig
import com.example.fincobox.data.SEARCH
import com.example.fincobox.data.TOP_HEADLINES
import com.example.fincobox.data.news.models.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApi {
    @GET(TOP_HEADLINES)
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Header("x-api-key") apiKey: String = BuildConfig.NEWS_API_KEY,
    ): NewsResponseDto

    @GET(SEARCH)
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Header("x-api-key") apiKey: String = BuildConfig.NEWS_API_KEY,
    ): NewsResponseDto
}