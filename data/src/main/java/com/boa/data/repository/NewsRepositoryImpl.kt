package com.boa.data.repository

import com.boa.data.datasource.NewsDataSource
import com.boa.data.mapper.NewsResponseToModelMapper
import com.boa.domain.model.News
import com.boa.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val newsDataSource: NewsDataSource,
    private val newsResponseToModelMapper: NewsResponseToModelMapper
) : NewsRepository {
    override suspend fun getNews(): List<News> {
        return newsResponseToModelMapper.mapAll(newsDataSource.getNews())
    }
}