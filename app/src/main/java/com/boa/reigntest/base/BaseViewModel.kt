package com.boa.reigntest.base

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

/**
 * Base ViewModel for using in Model-View-ViewModel architecture. Must be specified ViewState class.
 */
abstract class BaseViewModel<V> : ViewModel() {
    val resourceViewStatus: MediatorLiveData<V> =
        MediatorLiveData<V>().apply { postValue(getInitialViewState()) }

    abstract fun getInitialViewState(): V
    abstract fun initialize()
}