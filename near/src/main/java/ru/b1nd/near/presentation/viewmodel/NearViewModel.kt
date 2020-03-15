package ru.b1nd.near.presentation.viewmodel

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
import kotlinx.coroutines.withContext
import ru.b1nd.near.api.domain.usecase.FindCovidInfoUseCase
import ru.b1nd.near.api.domain.usecase.FindCovidInfoUseCase.FindCovidInfoEvent
import ru.b1nd.near.domain.entity.UserLocationInfo

class NearViewModel(
    private val findCovidInfoUseCase: FindCovidInfoUseCase
) : ViewModel() {

    private val stateRelay = MutableLiveData<NearPartialViewState>()

    @ExperimentalCoroutinesApi
    val state = stateRelay.asFlow()
        .scan(NearViewState()) { state, partial -> partial(state) }
        .distinctUntilChanged()
        .flowOn(Dispatchers.Main)

    fun onUserLocationChanged(userLocationInfo: UserLocationInfo) {
        viewModelScope.launch {
            val countryPredicate: suspend (String) -> Boolean = {
                it.equals(userLocationInfo.country, true) ||
                it.equals(userLocationInfo.countryCode, true)
            }
            val statePredicate: suspend (String?) -> Boolean = {
                it.equals(userLocationInfo.state, true) ||
                it.equals(userLocationInfo.admin, true)
            }
            withContext(Dispatchers.IO) {
                stateRelay.postValue(NearPartialViewStates.onUserLocationUpdated(userLocationInfo))
            }
            withContext(Dispatchers.IO) {
                findCovidInfoUseCase.execute(countryPredicate, null)
                    .also { event ->
                        when (event) {
                            is FindCovidInfoEvent.Success -> stateRelay.postValue(
                                NearPartialViewStates.onCountryCovidInfoUpdated(event.covidInfo)
                            )
                            is FindCovidInfoEvent.Empty -> stateRelay.postValue(
                                NearPartialViewStates.onEmptyCountryCovidInfo()
                            )
                        }
                    }
            }
            withContext(Dispatchers.IO) {
                findCovidInfoUseCase.execute(countryPredicate, statePredicate)
                    .also { event ->
                        when (event) {
                            is FindCovidInfoEvent.Success -> stateRelay.postValue(
                                NearPartialViewStates.onStateCovidInfoUpdated(event.covidInfo)
                            )
                            is FindCovidInfoEvent.Empty -> stateRelay.postValue(
                                NearPartialViewStates.onEmptyStateCovidInfo()
                            )
                        }
                    }
            }
        }
    }
}