package ru.b1nd.statistics.api.domain.usecase

import ru.b1nd.statistics.api.domain.entity.CountryInfo

interface GetCountriesInfoUseCase {
    sealed class GetCountriesInfoEvent {
        data class Success(val countriesInfo: List<CountryInfo>) : GetCountriesInfoEvent()
        data class Error(val throwable: Throwable): GetCountriesInfoEvent()
    }

    suspend fun execute(): GetCountriesInfoEvent
}