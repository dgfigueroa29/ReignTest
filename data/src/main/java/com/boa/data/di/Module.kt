package com.boa.data.di

import com.boa.data.datasource.NewsDataSource
import com.boa.data.datasource.remote.ApiProvider
import com.boa.data.datasource.remote.RemoteNewsDataSource
import com.boa.data.mapper.NewsResponseToModelMapper
import com.boa.data.repository.NewsRepositoryImpl
import com.boa.domain.repository.NewsRepository
import org.koin.dsl.module

val dataModule = module {
    single<NewsDataSource> { RemoteNewsDataSource(get()) }

    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }

    single { NewsResponseToModelMapper() }

    single { ApiProvider() }
    single {
        val apiProvider: ApiProvider = get()
        apiProvider.api
    }
}