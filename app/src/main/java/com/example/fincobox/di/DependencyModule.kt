package com.example.fincobox.di

import com.example.fincobox.data.NEWS_BASE_URL
import com.example.fincobox.data.news.NewsApi
import com.example.fincobox.data.news.repositories.NewsRepositoryImpl
import com.example.fincobox.domain.news.repositories.NewsRepository
import com.example.fincobox.domain.news.usecases.TopHeadlinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideTopHeadlinesUseCase(repo: NewsRepository): TopHeadlinesUseCase {
        return TopHeadlinesUseCase(repo)
    }

}