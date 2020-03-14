package ru.b1nd.near.presentation.viewmodel

import ru.b1nd.near.api.domain.entity.CovidInfo
import ru.b1nd.near.domain.entity.UserLocationInfo

data class NearViewState(
    val userLocationInfo: UserLocationInfo = UserLocationInfo(0.0, 0.0, "", "", "", ""),
    val covidInfo: CovidInfo = CovidInfo(0, 0, 0),
    val isStateInfo: Boolean = false
)