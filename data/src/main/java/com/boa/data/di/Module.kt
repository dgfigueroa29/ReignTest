package com.boa.data.di

import com.boa.data.datasource.LocalDataSource
import com.boa.data.datasource.RemoteDataSource
import com.boa.data.datasource.local.LocalNewsDataSource
import com.boa.data.datasource.local.db.AppDatabase
import com.boa.data.datasource.remote.ApiProvider
import com.boa.data.datasource.remote.RemoteNewsDataSource
import com.boa.data.mapper.NewsEntityToModelMapper
import com.boa.data.mapper.NewsModelToEntityMapper
import com.boa.data.mapper.NewsResponseToEntityMapper
import com.boa.data.mapper.NewsResponseToModelMapper
import com.boa.data.repository.NewsRepositoryImpl
import com.boa.domain.repository.NewsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<RemoteDataSource> { RemoteNewsDataSource(get(), get()) }

    single<LocalDataSource> { LocalNewsDataSource(get()) }

    single<NewsRepository> {
        NewsRepositoryImpl(
            get(), get(), get(),
            androidContext()
        )
    }

    single { NewsEntityToModelMapper() }
    single { NewsModelToEntityMapper() }
    single { NewsResponseToEntityMapper() }
    single { NewsResponseToModelMapper() }

    single { ApiProvider() }
    single {
        val apiProvider: ApiProvider = get()
        apiProvider.api
    }
    single { AppDatabase.getAppDatabase(androidContext()) }
}