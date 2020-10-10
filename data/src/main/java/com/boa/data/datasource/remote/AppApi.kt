package com.boa.data.datasource.remote

import com.boa.data.datasource.remote.response.NewsResponse
import com.boa.data.util.NEWS
import retrofit2.http.GET

interface AppApi {
    @GET(NEWS)
    suspend fun getNews(): List<NewsResponse>
}