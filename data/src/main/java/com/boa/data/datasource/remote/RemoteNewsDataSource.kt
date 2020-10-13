package com.boa.data.datasource.remote

import com.boa.data.datasource.NewsDataSource
import com.boa.data.entity.NewsEntity
import com.boa.data.mapper.NewsResponseToEntityMapper

class RemoteNewsDataSource(
    private val appApi: AppApi,
    private val newsResponseToEntityMapper: NewsResponseToEntityMapper
) : NewsDataSource {
    override suspend fun getNews(): List<NewsEntity> =
        newsResponseToEntityMapper.mapAll(appApi.getNews().hits)

    override suspend fun saveResult(entity: NewsEntity) {
    }

    override suspend fun updateResult(entity: NewsEntity) {
    }

    override suspend fun deleteResult(entity: NewsEntity) {
    }
}