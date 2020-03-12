package ru.b1nd.statistics.domain.usecase

import kotlinx.coroutines.flow.toList
import ru.b1nd.data.api.data.repo.CovidDataRepository
import ru.b1nd.statistics.api.domain.entity.CountryInfo
import ru.b1nd.statistics.api.domain.usecase.GetCountriesInfoUseCase
import ru.b1nd.statistics.api.domain.usecase.GetCountriesInfoUseCase.GetCountriesInfoEvent

class GetCountriesInfoUseCaseImpl(
    private val covidDataRepository: CovidDataRepository
) : GetCountriesInfoUseCase {

    override suspend fun execute(): GetCountriesInfoEvent = runCatching {
        GetCountriesInfoEvent.Success(
            covidDataRepository.getData().toList()
                .groupBy { it.country }
                .map { (country, values) ->
                    CountryInfo(
                        country,
                        values.fold(0L) { acc, it -> acc + it.confirmed },
                        values.fold(0L) { acc, it -> acc + it.deaths },
                        values.fold(0L) { acc, it -> acc + it.recovered }
                    )
                }
        )
    }.getOrElse { GetCountriesInfoEvent.Error(it) }
}