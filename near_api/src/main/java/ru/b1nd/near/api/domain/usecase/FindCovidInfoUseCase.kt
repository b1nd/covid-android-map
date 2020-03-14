package ru.b1nd.near.api.domain.usecase

import ru.b1nd.near.api.domain.entity.CovidInfo

interface FindCovidInfoUseCase {
    sealed class FindCovidInfoEvent {
        data class Success(val covidInfo: CovidInfo) : FindCovidInfoEvent()
        data class Error(val throwable: Throwable) : FindCovidInfoEvent()
        object Empty : FindCovidInfoEvent()
    }

    suspend fun execute(
        countryPredicate: suspend (String) -> Boolean,
        statePredicate: (suspend (String?) -> Boolean)?
    ): FindCovidInfoEvent
}