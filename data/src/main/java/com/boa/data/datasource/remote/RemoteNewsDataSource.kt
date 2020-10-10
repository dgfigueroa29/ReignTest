package com.boa.data.datasource.remote

import com.boa.data.datasource.NewsDataSource
import com.boa.data.datasource.remote.response.NewsResponse

class RemoteNewsDataSource(private val appApi: AppApi) : NewsDataSource {
    override suspend fun getNews(): List<NewsResponse> = appApi.getNews().hits
}