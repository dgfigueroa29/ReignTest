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
        try {
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
                    val localNew =
                        localNewsEntities.firstOrNull { it.objectID == newsEntity.objectID }

                    if (localNew == null) {
                        news.add(newsEntityToModelMapper.map(newsEntity))
                        localDataSource.save(newsEntity)
                    } else {
                        if (localNew.isDeleted == 0) {
                            news.add(newsEntityToModelMapper.map(newsEntity))
                        }
                    }
                }

                localNewsEntities.filter { it.isDeleted == 0 }
                    .forEach { localDataSource.delete(it) }
            }

            news.sortedBy { it.createdAt }
        } catch (e: Exception) {
            println("Error: $e")
        }

        return news.filter { !it.isDeleted }
    }

    override suspend fun deleteNews(objectId: String): News {
        val entity = localDataSource.getById(objectId)
        try {
            entity.isDeleted = 1
            localDataSource.update(entity)
        } catch (e: Exception) {
            println("Error: $e")
        }

        return newsEntityToModelMapper.map(entity)
    }
}