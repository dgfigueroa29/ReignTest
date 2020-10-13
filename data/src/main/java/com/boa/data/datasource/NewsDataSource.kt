package com.boa.data.datasource

import com.boa.data.entity.NewsEntity

interface NewsDataSource {
    suspend fun getNews(): List<NewsEntity>
    suspend fun saveResult(entity: NewsEntity)
    suspend fun updateResult(entity: NewsEntity)
    suspend fun deleteResult(entity: NewsEntity)
}