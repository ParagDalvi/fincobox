package com.example.fincobox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fincobox.presentation.article_details.ui.ArticleDetailsPage
import com.example.fincobox.presentation.news.ui.HeadlinesPage

@Composable
fun AppGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoutes.HEADLINES_ROUTE) {

        composable(route = AppRoutes.HEADLINES_ROUTE) {
            HeadlinesPage(navController)
        }

        composable(route = AppRoutes.ARTICLE_DETAIL_ROUTE) {
            ArticleDetailsPage(navController)
        }
    }
}