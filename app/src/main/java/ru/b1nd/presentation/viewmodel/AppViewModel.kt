package ru.b1nd.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.scan
import ru.b1nd.router.AppRouter

class AppViewModel constructor(
    private val router: AppRouter
) : ViewModel() {
    private val stateRelay = MutableLiveData<AppPartialViewState>()

    @ExperimentalCoroutinesApi
    val state = stateRelay.asFlow()
        .scan(AppViewState()) { state, partial -> partial(state) }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)

    fun onScreenShown(shouldShowNavigationBar: Boolean) {
        stateRelay.postValue(AppPartialViewStates.onScreenShow(shouldShowNavigationBar))
    }

    fun onMapNavigation() {
        stateRelay.postValue(AppPartialViewStates.onMapNavigation())
        router.onMap()
    }

    fun onNearNavigation() {
        stateRelay.postValue(AppPartialViewStates.onNearNavigation())
        router.onNear()
    }

    fun onStatisticsNavigation() {
        stateRelay.postValue(AppPartialViewStates.onStatisticsNavigation())
        router.onStatistics()
    }

    fun onBack() {
        router.onBack()
    }
}