package com.boa.reigntest.ui.main

import com.boa.domain.base.BaseException
import com.boa.domain.base.BaseStatusObserver
import com.boa.domain.model.News
import com.boa.domain.usecase.DeleteNewsUseCase
import com.boa.domain.usecase.GetNewsUseCase
import com.boa.reigntest.base.BaseViewModel

class MainViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase
) : BaseViewModel<MainViewStatus>() {
    private var newsList: List<News> = listOf()

    override fun getInitialViewState(): MainViewStatus = MainViewStatus()

    private fun onError(exception: BaseException?) {
        exception?.let {
            val mainViewStatus = getInitialViewState()
            mainViewStatus.isError = true
            mainViewStatus.errorMessage = it.message ?: ""
            resourceViewStatus.value = mainViewStatus
        }
    }

    private fun onLoading(progress: Int) {
        val mainViewStatus = getInitialViewState()
        mainViewStatus.isLoading = progress > 100
        resourceViewStatus.value = mainViewStatus
    }

    override fun initialize() {
        val mainViewStatus = getInitialViewState()
        BaseStatusObserver(
            resourceViewStatus, getNewsUseCase.execute(null),
            {
                newsList = it ?: newsList
                mainViewStatus.isReady = true
                mainViewStatus.newsList = newsList
                resourceViewStatus.value = mainViewStatus
            },
            this::onError,
            this::onLoading
        )
    }

    fun delete(objectId: String) {
        val mainViewStatus = getInitialViewState()
        BaseStatusObserver(
            resourceViewStatus, deleteNewsUseCase.execute(DeleteNewsUseCase.Params(objectId)),
            {
                newsList = newsList.filter { news -> news.objectID != objectId }
                mainViewStatus.isReady = true
                mainViewStatus.newsList = newsList
                resourceViewStatus.value = mainViewStatus
            },
            this::onError,
            this::onLoading
        )
    }
}