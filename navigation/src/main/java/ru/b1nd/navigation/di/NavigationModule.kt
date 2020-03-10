package ru.b1nd.navigation.di

import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

val navigationModule = module {
    val cicerone = Cicerone.create()
    single<Router> { cicerone.router }
    single<NavigatorHolder> { cicerone.navigatorHolder }
}