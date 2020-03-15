package ru.b1nd.near.presentation.viewmodel

import ru.b1nd.near.api.domain.entity.CovidInfo
import ru.b1nd.near.domain.entity.UserLocationInfo

data class NearViewState(
    val userLocationInfo: UserLocationInfo = UserLocationInfo(0.0, 0.0, "", "", "", ""),
    val isCountryCovidInfoLoaded: Boolean = false,
    val countryCovidInfo: CovidInfo = CovidInfo(0, 0, 0),
    val isStateCovidInfoLoaded: Boolean = false,
    val stateCovidInfo: CovidInfo = CovidInfo(0, 0, 0)
)