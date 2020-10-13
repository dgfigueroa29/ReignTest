package com.boa.data.datasource.local

import com.boa.data.datasource.NewsDataSource
import com.boa.data.datasource.local.db.AppDatabase
import com.boa.data.entity.NewsEntity

class LocalNewsDataSource(private val database: AppDatabase) : NewsDataSource {
    override suspend fun getNews(): List<NewsEntity> = database.newsDao().getAll()

    override suspend fun saveResult(entity: NewsEntity) {
        database.newsDao().insert(entity)
    }

    override suspend fun updateResult(entity: NewsEntity) {
        database.newsDao().update(entity)
    }

    override suspend fun deleteResult(entity: NewsEntity) {
        database.newsDao().delete(entity)
    }
}