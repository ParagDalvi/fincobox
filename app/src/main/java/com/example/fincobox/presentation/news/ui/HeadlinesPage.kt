package com.example.fincobox.presentation.news.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.fincobox.domain.news.models.Article
import com.example.fincobox.domain.news.models.Source
import com.example.fincobox.presentation.news.viewmodels.NewsViewmodel

@Composable
fun HeadlinesPage(newsViewModel: NewsViewmodel = hiltViewModel()) {
    val articles = newsViewModel.topArticles.collectAsLazyPagingItems()

    Column {
        TextField(
            value = "",
            onValueChange = { query ->
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Search News") }
        )

        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            items(
                count = articles.itemCount,
            ) { index ->
                articles[index]?.let { NewsItem(article = it) }
            }

            when (articles.loadState.append) {
                is LoadState.Error -> {

                }

                is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }

                is LoadState.NotLoading -> Unit
            }
        }
    }
}

@Composable
fun NewsItem(article: Article) {
    Text(text = article.title)
}

@Preview
@Composable
private fun NewsItemPreview() {
    NewsItem(
        Article(
            author = "Someone",
            content = "Data",
            description = "More details",
            imageUrl = "",
            publishedAt = "22 Jan",
            source = Source(
                id = "",
                name = ""
            ),
            title = "Title",
            url = "google.com"
        )
    )
}