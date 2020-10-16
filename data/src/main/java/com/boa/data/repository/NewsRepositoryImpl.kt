package com.boa.data.repository

import android.content.Context
import com.boa.data.datasource.LocalDataSource
import com.boa.data.datasource.RemoteDataSource
import com.boa.data.mapper.NewsEntityToModelMapper
import com.boa.data.util.isOnline
import com.boa.domain.model.News
import com.boa.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val newsEntityToModelMapper: NewsEntityToModelMapper,
    private val context: Context
) : NewsRepository {
    override suspend fun getNews(): List<News> {
        val news: MutableList<News> = mutableListOf()
        val localNewsEntities = localDataSource.getAll()
        val remoteNewsEntities = if (isOnline(context)) {
            remoteDataSource.getAll()
        } else {
            listOf()
        }

        if (remoteNewsEntities.isEmpty()) {
            news.addAll(newsEntityToModelMapper.mapAll(localNewsEntities))
        } else {
            remoteNewsEntities.forEach { newsEntity ->
                if (!localNewsEntities.any { it.objectID == newsEntity.objectID }) {
                    localDataSource.save(newsEntity)
                    news.add(newsEntityToModelMapper.map(newsEntity))
                }
            }

            localNewsEntities.forEach { localDataSource.delete(it) }
        }

        news.sortedBy { it.createdAt }
        return news.filter { !it.isDeleted }
    }

    override suspend fun deleteNews(objectId: String): News {
        val entity = localDataSource.getById(objectId)
        entity.isDeleted = 1
        localDataSource.update(entity)
        return newsEntityToModelMapper.map(entity)
    }
}