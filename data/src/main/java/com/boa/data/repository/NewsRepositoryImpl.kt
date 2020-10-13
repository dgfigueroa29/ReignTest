package com.boa.data.repository

import android.content.Context
import com.boa.data.datasource.local.LocalNewsDataSource
import com.boa.data.datasource.remote.RemoteNewsDataSource
import com.boa.data.mapper.NewsEntityToModelMapper
import com.boa.data.util.isOnline
import com.boa.domain.model.News
import com.boa.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val remoteNewsDataSource: RemoteNewsDataSource,
    private val localNewsDataSource: LocalNewsDataSource,
    private val newsEntityToModelMapper: NewsEntityToModelMapper,
    private val context: Context
) : NewsRepository {
    override suspend fun getNews(): List<News> {
        val news: MutableList<News> = mutableListOf()
        val localNewsEntities = localNewsDataSource.getNews()
        val remoteNewsEntities = if (isOnline(context)) {
            remoteNewsDataSource.getNews()
        } else {
            listOf()
        }

        if (remoteNewsEntities.isEmpty()) {
            news.addAll(newsEntityToModelMapper.mapAll(localNewsEntities))
        } else {
            remoteNewsEntities.forEach { newsEntity ->
                if (!localNewsEntities.any { it.objectID == newsEntity.objectID }) {
                    localNewsDataSource.saveResult(newsEntity)
                    news.add(newsEntityToModelMapper.map(newsEntity))
                }
            }
            localNewsEntities.forEach { localNewsDataSource.deleteResult(it) }
        }

        news.sortedBy { it.createdAt }
        return news.filter { !it.isDeleted }
    }
}