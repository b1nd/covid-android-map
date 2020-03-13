package ru.b1nd.map.domain.usecase

import kotlinx.coroutines.flow.*
import ru.b1nd.data.api.data.repo.CovidDataRepository
import ru.b1nd.map.api.domain.entity.GeoInfo
import ru.b1nd.map.api.domain.usecase.GetGeoInfoUseCase
import ru.b1nd.map.api.domain.usecase.GetGeoInfoUseCase.GetGeoInfoEvent

class GetGeoInfoUseCaseImpl(
    private val covidDataRepository: CovidDataRepository
) : GetGeoInfoUseCase {
    override suspend fun execute(): GetGeoInfoEvent = runCatching {
        if (covidDataRepository.isDataLoaded()) {
            GetGeoInfoEvent.Success(
                covidDataRepository.getData()
                    .filter { it.confirmed != 0L || it.deaths != 0L || it.recovered != 0L }
                    .map {
                        GeoInfo(
                            it.latitude,
                            it.longitude,
                            it.state,
                            it.country,
                            it.confirmed,
                            it.deaths,
                            it.recovered
                        )
                    }.toList()
            )
        } else {
            GetGeoInfoEvent.Empty
        }
    }.getOrElse { GetGeoInfoEvent.Error(it) }
}