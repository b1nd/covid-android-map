package ru.b1nd.map.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import ru.b1nd.map.api.domain.usecase.GetGeoInfoUseCase
import ru.b1nd.map.api.domain.usecase.GetGeoInfoUseCase.GetGeoInfoEvent

class MapViewModel(
    private val getGeoInfoUseCase: GetGeoInfoUseCase
) : ViewModel() {

    private val stateRelay = MutableLiveData<MapPartialViewState>()

    @ExperimentalCoroutinesApi
    val state = stateRelay.asFlow()
        .scan(MapViewState()) { state, partial -> partial(state) }
        .distinctUntilChanged()
        .flowOn(Dispatchers.Main)

    fun onGeoInfoRequired() {
        viewModelScope.launch {
            getGeoInfoUseCase.execute()
                .also { event ->
                    when (event) {
                        is GetGeoInfoEvent.Success ->
                            stateRelay.postValue(MapPartialViewStates.onGeoInfoLoaded(event.geoInfoList))
                    }
                }
        }
    }
}