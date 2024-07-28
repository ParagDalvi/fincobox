package com.example.fincobox.domain.news.models

import com.example.fincobox.data.news.models.ArticleDto
import com.example.fincobox.data.news.models.NewsResponseDto
import com.example.fincobox.data.news.models.SourceDto

data class NewsData(
    val totalArticles: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val publishedAt: String,
    val content: String
)

data class Source(
    val id: String,
    val name: String
)

fun NewsResponseDto.toNewsData(): NewsData {
    return NewsData(
        totalArticles = this.totalResults ?: 0,
        articles = this.articles?.mapNotNull { it?.toArticle() } ?: emptyList()
    )
}

private fun ArticleDto.toArticle(): Article {
    return Article(
        source = this.source?.toSource() ?: Source("", ""),
        author = this.author ?: "",
        title = this.title ?: "",
        description = this.description ?: "",
        url = this.url ?: "",
        imageUrl = this.urlToImage ?: "",
        publishedAt = this.publishedAt ?: "",
        content = this.content ?: ""
    )
}

private fun SourceDto.toSource(): Source {
    return Source(
        id = this.id ?: "",
        name = this.name ?: ""
    )
}