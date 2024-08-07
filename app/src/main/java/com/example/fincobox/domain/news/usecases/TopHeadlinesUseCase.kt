package com.example.fincobox.domain.news.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.fincobox.data.news.models.NewsType
import com.example.fincobox.data.news.repositories.NewsPagingSource
import com.example.fincobox.domain.news.models.Article
import com.example.fincobox.domain.news.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopHeadlinesUseCase @Inject constructor(repo: NewsRepository) {
    private val newsPagingSource = NewsPagingSource(newsType = NewsType.Headlines, repo = repo)

    operator fun invoke(): Flow<PagingData<Article>> {
        return Pager(PagingConfig(NewsPagingSource.ARTICLES_PAGE_SIZE)) { newsPagingSource }.flow
    }
}