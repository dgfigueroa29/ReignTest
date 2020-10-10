package com.boa.domain.usecase

import com.boa.domain.base.BaseUseCase
import com.boa.domain.model.News
import com.boa.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope

class GetNewsUseCase(
    scope: CoroutineScope,
    private val newsRepository: NewsRepository
) : BaseUseCase<List<News>, Any?>(scope) {
    override suspend fun getData(param: Any?): List<News> =
        newsRepository.getNews()
}