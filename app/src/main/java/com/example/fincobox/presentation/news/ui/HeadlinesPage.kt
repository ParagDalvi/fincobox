package com.example.fincobox.presentation.news.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.fincobox.R
import com.example.fincobox.domain.news.models.Article
import com.example.fincobox.domain.news.models.Source
import com.example.fincobox.presentation.news.viewmodels.NewsViewmodel

@Composable
fun HeadlinesPage(newsViewModel: NewsViewmodel = hiltViewModel()) {
    val articles = newsViewModel.topArticles.collectAsLazyPagingItems()
    val searchedArticles = newsViewModel.searchedArticles.collectAsLazyPagingItems()
    val hasUserSearched = newsViewModel.hasUserSearched.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        item { SearchBox() }

        if (hasUserSearched.value) {
            items(
                count = searchedArticles.itemCount,
            ) { index ->
                searchedArticles[index]?.let { NewsItem(article = it) }
            }

            item { NonSuccessState(searchedArticles) }

        } else {
            items(
                count = articles.itemCount,
            ) { index ->
                articles[index]?.let { NewsItem(article = it) }
            }

            item { NonSuccessState(articles) }
        }
    }
}

@Composable
fun NonSuccessState(articles: LazyPagingItems<*>) {
    when (articles.loadState.append) {
        is LoadState.Error -> {
            ErrorState(
                message = (articles.loadState.append as LoadState.Error)
                    .error
                    .message
            )
        }

        is LoadState.Loading -> {
            CircularProgressIndicator()
        }

        is LoadState.NotLoading -> Unit
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
fun NewsItem(article: Article, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            model = article.imageUrl,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(3f),
        ) {
            Text(
                article.title,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                article.content,
                fontWeight = FontWeight.Thin,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    article.publishedAt,
                    fontWeight = FontWeight.Thin,
                    maxLines = 2
                )
                Text("Read more >", color = Color.Yellow)
            }
        }
    }
}

@Composable
fun ErrorState(message: String?) {
    if (message != null) {
        Text(
            text = message,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsItemPreview() {
    Column {
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
}