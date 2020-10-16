package com.boa.data.datasource

import com.boa.data.entity.NewsEntity

interface RemoteDataSource {
    suspend fun getAll(): List<NewsEntity>
}