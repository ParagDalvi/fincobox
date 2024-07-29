package com.example.fincobox.presentation.news.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val searchedArticles = newsViewModel.searchedArticles.collectAsLazyPagingItems()
    val hasUserSearched = newsViewModel.hasUserSearched.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        item { SearchBox() }

        if(hasUserSearched.value) {
            items(
                count = searchedArticles.itemCount,
            ) { index ->
                searchedArticles[index]?.let { NewsItem(article = it) }
            }

            when (searchedArticles.loadState.append) {
                is LoadState.Error -> {
                    item {
                        ErrorState(
                            message = (searchedArticles.loadState.append as LoadState.Error)
                                .error
                                .localizedMessage
                        )
                    }
                }

                is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }

                is LoadState.NotLoading -> Unit
            }
        } else {
            items(
                count = articles.itemCount,
            ) { index ->
                articles[index]?.let { NewsItem(article = it) }
            }

            when (articles.loadState.append) {
                is LoadState.Error -> {
                    item {
                        ErrorState(
                            message = (articles.loadState.append as LoadState.Error)
                                .error
                                .localizedMessage
                        )
                    }
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
fun SearchBox(newsViewModel: NewsViewmodel = hiltViewModel()) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            newsViewModel.performSearch(it)
        },
        label = { Text("Search News") },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun NewsItem(article: Article) {
    Text(text = article.title)
}

@Composable
fun ErrorState(message: String?) {
    if (message != null) {
        Text(
            text = message,
        )
    }
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


@Preview
@Composable
private fun SearchBoxPreview() {
    SearchBox()
}