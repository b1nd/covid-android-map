package ru.b1nd.near.presentation.viewmodel

import ru.b1nd.near.api.domain.entity.CovidInfo
import ru.b1nd.near.domain.entity.UserLocationInfo

typealias NearPartialViewState = (NearViewState) -> NearViewState

object NearPartialViewStates {

    fun onUserLocationUpdated(userLocationInfo: UserLocationInfo): NearPartialViewState = {
        it.copy(
            userLocationInfo = userLocationInfo
        )
    }

    fun onStateCovidInfoUpdated(stateCovidInfo: CovidInfo): NearPartialViewState = {
        it.copy(
            isStateCovidInfoLoaded = true,
            stateCovidInfo = stateCovidInfo
        )
    }

    fun onCountryCovidInfoUpdated(countryCovidInfo: CovidInfo): NearPartialViewState = {
        it.copy(
            isCountryCovidInfoLoaded = true,
            countryCovidInfo = countryCovidInfo
        )
    }

    fun onEmptyStateCovidInfo(): NearPartialViewState = {
        it.copy(
            isStateCovidInfoLoaded = false,
            stateCovidInfo = CovidInfo(0, 0, 0)
        )
    }

    fun onEmptyCountryCovidInfo(): NearPartialViewState = {
        it.copy(
            isCountryCovidInfoLoaded = false,
            countryCovidInfo = CovidInfo(0, 0, 0)
        )
    }
}