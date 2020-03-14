package ru.b1nd.near.presentation.viewmodel

import ru.b1nd.near.api.domain.entity.CovidInfo
import ru.b1nd.near.domain.entity.UserLocationInfo

typealias MapPartialViewState = (NearViewState) -> NearViewState

object NearPartialViewStates {

    fun onStateCovidInfoUpdated(covidInfo: CovidInfo, userLocationInfo: UserLocationInfo): MapPartialViewState = {
        it.copy(
            userLocationInfo = userLocationInfo,
            isStateInfo = true,
            covidInfo = covidInfo
        )
    }

    fun onCountryCovidInfoUpdated(covidInfo: CovidInfo, userLocationInfo: UserLocationInfo): MapPartialViewState = {
        it.copy(
            userLocationInfo = userLocationInfo,
            isStateInfo = false,
            covidInfo = covidInfo
        )
    }
}