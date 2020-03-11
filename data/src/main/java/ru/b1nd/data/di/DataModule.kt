package ru.b1nd.data.di

import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import ru.b1nd.data.api.data.repo.CovidDataRepository
import ru.b1nd.data.data.repo.CovidDataRepositoryImpl

@InternalCoroutinesApi
val dataModule = module {
    singleBy<CovidDataRepository, CovidDataRepositoryImpl>()
}