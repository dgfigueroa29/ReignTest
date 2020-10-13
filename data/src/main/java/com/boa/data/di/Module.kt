package com.boa.data.di

import com.boa.data.datasource.NewsDataSource
import com.boa.data.datasource.local.LocalNewsDataSource
import com.boa.data.datasource.local.db.AppDatabase
import com.boa.data.datasource.remote.ApiProvider
import com.boa.data.datasource.remote.RemoteNewsDataSource
import com.boa.data.mapper.NewsResponseToModelMapper
import com.boa.data.repository.NewsRepositoryImpl
import com.boa.domain.repository.NewsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<NewsDataSource> { RemoteNewsDataSource(get(), get()) }

    single<NewsDataSource> { LocalNewsDataSource(get()) }

    single<NewsRepository> {
        NewsRepositoryImpl(
            get(), get(), get(),
            androidContext()
        )
    }

    single { NewsResponseToModelMapper() }

    single { ApiProvider() }
    single {
        val apiProvider: ApiProvider = get()
        apiProvider.api
    }
    single { AppDatabase.getAppDatabase(androidContext()) }
}