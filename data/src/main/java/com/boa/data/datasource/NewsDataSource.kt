package com.boa.data.datasource

import com.boa.data.datasource.remote.response.NewsResponse

interface NewsDataSource {
    suspend fun getNews(): List<NewsResponse>
}