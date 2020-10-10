package com.boa.reigntest.di

import com.boa.reigntest.ui.detail.DetailViewModel
import com.boa.reigntest.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel() }
}