package com.boa.data.datasource.local

import com.boa.data.datasource.LocalDataSource
import com.boa.data.datasource.local.db.AppDatabase
import com.boa.data.entity.NewsEntity

class LocalNewsDataSource(private val database: AppDatabase) : LocalDataSource {
    override suspend fun getAll(): List<NewsEntity> = database.newsDao().getAll()

    override suspend fun getById(objectId: String): NewsEntity =
        database.newsDao().getById(objectId.toLong())

    override suspend fun save(entity: NewsEntity) {
        database.newsDao().insert(entity)
    }

    override suspend fun update(entity: NewsEntity) {
        database.newsDao().update(entity)
    }

    override suspend fun delete(entity: NewsEntity) {
        database.newsDao().delete(entity)
    }
}