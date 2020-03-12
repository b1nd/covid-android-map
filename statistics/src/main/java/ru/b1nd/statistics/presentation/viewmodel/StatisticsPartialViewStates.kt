package ru.b1nd.statistics.presentation.viewmodel

import ru.b1nd.statistics.api.domain.entity.CountryInfo
import ru.b1nd.statistics.api.domain.entity.OverallInfo

typealias StatisticsPartialViewState = (StatisticsViewState) -> StatisticsViewState

object StatisticsPartialViewStates {

    fun onOverallInfoLoaded(overallInfo: OverallInfo): StatisticsPartialViewState = {
        it.copy(
            overallInfo = overallInfo
        )
    }

    fun onCountriesInfoLoaded(countriesInfo: List<CountryInfo>): StatisticsPartialViewState = {
        it.copy(
            countriesInfo = countriesInfo
        )
    }
}