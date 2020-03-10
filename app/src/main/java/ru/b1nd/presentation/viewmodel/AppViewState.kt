package ru.b1nd.presentation.viewmodel

data class AppViewState(
    val shouldShowBottomNavigation: Boolean = true,
    val bottomState: BottomState = BottomState.Near
)

enum class BottomState {
    Map, Near, Statistics
}