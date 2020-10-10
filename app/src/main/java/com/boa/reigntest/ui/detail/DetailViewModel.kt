package com.boa.reigntest.ui.detail

import com.boa.reigntest.base.BaseViewModel

class DetailViewModel : BaseViewModel<DetailViewStatus>() {
    var url: String = ""
    override fun getInitialViewState(): DetailViewStatus = DetailViewStatus()

    override fun initialize() {
        resourceViewState.value = DetailViewStatus(url)
    }
}