package com.boa.data.datasource.remote

import com.boa.data.datasource.RemoteDataSource
import com.boa.data.entity.NewsEntity
import com.boa.data.mapper.NewsResponseToEntityMapper

class RemoteNewsDataSource(
    private val appApi: AppApi,
    private val newsResponseToEntityMapper: NewsResponseToEntityMapper
) : RemoteDataSource {
    override suspend fun getAll(): List<NewsEntity> =
        newsResponseToEntityMapper.mapAll(appApi.getNews().hits)
}