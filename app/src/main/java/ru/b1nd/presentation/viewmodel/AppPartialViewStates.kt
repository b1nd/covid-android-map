package ru.b1nd.presentation.viewmodel

typealias AppPartialViewState = (AppViewState) -> AppViewState

object AppPartialViewStates {

    fun onScreenShow(shouldShowNavigationBar: Boolean): AppPartialViewState = { previousViewState ->
        previousViewState.copy(
            shouldShowBottomNavigation = shouldShowNavigationBar
        )
    }

    fun onMapNavigation(): AppPartialViewState = { previousViewState ->
        previousViewState.copy(
            shouldShowBottomNavigation = true,
            bottomState = BottomState.Map
        )
    }

    fun onNearNavigation(): AppPartialViewState = { previousViewState ->
        previousViewState.copy(
            shouldShowBottomNavigation = true,
            bottomState = BottomState.Near
        )
    }

    fun onStatisticsNavigation(): AppPartialViewState = { previousViewState ->
        previousViewState.copy(
            shouldShowBottomNavigation = true,
            bottomState = BottomState.Statistics
        )
    }
}