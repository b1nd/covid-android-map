package ru.b1nd.router

import ru.b1nd.Screens
import ru.terrakok.cicerone.Router

class AppRouter(
    private val router: Router
) {

    fun onMap() {
        router.newRootScreen(Screens.Map)
    }

    fun onNear() {
        router.newRootScreen(Screens.Near)
    }

    fun onStatistics() {
        router.newRootScreen(Screens.Statistics)
    }

    fun onBack() {
        router.exit()
    }
}