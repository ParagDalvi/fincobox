package com.example.fincobox.data.news.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.fincobox.domain.news.models.Article
import com.example.fincobox.domain.news.models.toArticle
import com.example.fincobox.domain.news.repositories.NewsRepository
import java.io.IOException
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val repo: NewsRepository
) : PagingSource<Int, Article>() {

    companion object {
        private const val NEWS_START_INDEX = 1
        const val ARTICLES_PAGE_SIZE = 20
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: NEWS_START_INDEX
        return try {
            val response = repo.getTopHeadlines(position, ARTICLES_PAGE_SIZE)
            val articles = response.articles?.mapNotNull { it?.toArticle() } ?: emptyList()

            LoadResult.Page(
                data = articles,
                prevKey = if (position == NEWS_START_INDEX) null else position - 1,
                nextKey = if (response.articles?.isEmpty() == true) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}