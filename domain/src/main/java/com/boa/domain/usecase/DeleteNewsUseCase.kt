package com.boa.domain.usecase

import com.boa.domain.base.BaseUseCase
import com.boa.domain.model.News
import com.boa.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope

class DeleteNewsUseCase(
    scope: CoroutineScope,
    private val newsRepository: NewsRepository
) : BaseUseCase<News, DeleteNewsUseCase.Params>(scope) {
    override suspend fun getData(param: Params): News =
        newsRepository.deleteNews(param.objectId)

    data class Params(val objectId: String)
}