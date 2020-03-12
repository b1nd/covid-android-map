package ru.b1nd.statistics.presentation.viewmodel

import ru.b1nd.statistics.api.domain.entity.CountryInfo
import ru.b1nd.statistics.api.domain.entity.OverallInfo

data class StatisticsViewState(
    val overallInfo: OverallInfo = OverallInfo(0, 0, 0),
    val countriesInfo: List<CountryInfo> = listOf()
)