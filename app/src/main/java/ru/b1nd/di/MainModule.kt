package ru.b1nd.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.b1nd.presentation.viewmodel.AppViewModel
import ru.b1nd.router.AppRouter

val routerModule = module {
    single { AppRouter(get()) }
    viewModel { AppViewModel(get()) }
}