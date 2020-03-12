package ru.b1nd.statistics.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.scan
import ru.b1nd.statistics.api.domain.usecase.GetCountriesInfoUseCase
import ru.b1nd.statistics.api.domain.usecase.GetCountriesInfoUseCase.GetCountriesInfoEvent
import ru.b1nd.statistics.api.domain.usecase.GetOverallInfoUseCase
import ru.b1nd.statistics.api.domain.usecase.GetOverallInfoUseCase.GetOverallInfoEvent

class StatisticsViewModel(
    private val getOverallInfoUseCase: GetOverallInfoUseCase,
    private val getCountriesInfoUseCase: GetCountriesInfoUseCase
) : ViewModel() {

    private val stateRelay = MutableLiveData<StatisticsPartialViewState>()

    @ExperimentalCoroutinesApi
    val state = stateRelay.asFlow()
        .scan(StatisticsViewState()) { state, partial -> partial(state) }
        .distinctUntilChanged()
        .flowOn(Dispatchers.Main)

    fun onInfoRequired() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getOverallInfoUseCase.execute()
                    .also { event ->
                        when (event) {
                            is GetOverallInfoEvent.Success ->
                                stateRelay.postValue(StatisticsPartialViewStates.onOverallInfoLoaded(event.overallInfo))
                        }
                    }
            }
            withContext(Dispatchers.IO) {
                getCountriesInfoUseCase.execute()
                    .also { event ->
                        when (event) {
                            is GetCountriesInfoEvent.Success ->
                                stateRelay.postValue(StatisticsPartialViewStates.onCountriesInfoLoaded(event.countriesInfo))
                        }
                    }
            }
        }
    }
}