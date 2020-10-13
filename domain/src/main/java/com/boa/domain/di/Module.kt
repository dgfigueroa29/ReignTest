package com.boa.domain.di

import com.boa.domain.usecase.DeleteNewsUseCase
import com.boa.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {
    factory { GetNewsUseCase(get(), get()) }
    factory { DeleteNewsUseCase(get(), get()) }

    single { CoroutineScope(Dispatchers.IO) }
}