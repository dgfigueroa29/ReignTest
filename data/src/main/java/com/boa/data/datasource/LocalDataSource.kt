package com.boa.data.datasource

import com.boa.data.entity.NewsEntity

interface LocalDataSource {
    suspend fun getAll(): List<NewsEntity>
    suspend fun getById(objectId: String): NewsEntity
    suspend fun save(entity: NewsEntity)
    suspend fun update(entity: NewsEntity)
    suspend fun delete(entity: NewsEntity)
}