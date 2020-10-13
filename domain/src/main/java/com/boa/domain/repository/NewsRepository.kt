package com.boa.domain.repository

import com.boa.domain.model.News

interface NewsRepository {
    suspend fun getNews(): List<News>
    suspend fun deleteNews(objectId: String): News
}