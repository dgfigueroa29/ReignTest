package com.boa.reigntest.ui.main

import com.boa.domain.base.BaseException
import com.boa.reigntest.base.BaseViewModel

class MainViewModel : BaseViewModel<MainViewStatus>() {
    override fun getInitialViewState(): MainViewStatus = MainViewStatus()

    private fun onError(exception: BaseException?) {
        exception?.let {
            val mainViewStatus = getInitialViewState()
            mainViewStatus.isError = true
            mainViewStatus.errorMessage = it.message ?: ""
            resourceViewState.value = mainViewStatus
        }
    }

    private fun onLoading(progress: Int) {
        val mainViewStatus = getInitialViewState()
        mainViewStatus.isLoading = progress > 100
        resourceViewState.value = mainViewStatus
    }

    override fun initialize() {
    }
}