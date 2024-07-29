package com.example.fincobox.data.news.models

sealed class NewsType {
    data object Headlines: NewsType()

    data class SearchNews(val query: String): NewsType()
}