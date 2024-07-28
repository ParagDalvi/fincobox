package com.example.fincobox.presentation.news.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fincobox.domain.news.models.Article
import com.example.fincobox.presentation.news.viewmodels.NewsViewmodel

@Composable
fun HeadlinesPage(newsViewModel: NewsViewmodel = hiltViewModel()) {
    val newsState = newsViewModel.newsState.observeAsState().value

    Column {
        TextField(
            value = "",
            onValueChange = { query ->
                if (query.isNotEmpty()) {
//                    newsViewModel.searchNews(query)
                } else {
//                    newsViewModel.getTopHeadlines()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Search News") }
        )
        LazyColumn {
            items(newsState?.articles ?: emptyList()) { article ->
                NewsItem(article = article)
            }
        }
    }
}

@Composable
fun NewsItem(article: Article) {
    Text(text = article.title)
}