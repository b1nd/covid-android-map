package ru.b1nd.statistics.domain.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import ru.b1nd.data.api.data.repo.CovidDataRepository
import ru.b1nd.statistics.api.domain.entity.OverallInfo
import ru.b1nd.statistics.api.domain.usecase.GetOverallInfoUseCase
import ru.b1nd.statistics.api.domain.usecase.GetOverallInfoUseCase.GetOverallInfoEvent

class GetOverallInfoUseCaseImpl(
    private val covidDataRepository: CovidDataRepository
) : GetOverallInfoUseCase {

    override suspend fun execute(): GetOverallInfoEvent = coroutineScope {
        runCatching {
            val rows = covidDataRepository.getData().toList()

            val totalInfected  = async { rows.fold(0L) { acc, it -> acc + it.confirmed } }
            val totalDeaths    = async { rows.fold(0L) { acc, it -> acc + it.deaths } }
            val totalRecovered = async { rows.fold(0L) { acc, it -> acc + it.recovered } }

            GetOverallInfoEvent.Success(OverallInfo(
                totalInfected.await(),
                totalDeaths.await(),
                totalRecovered.await()
            ))
        }.getOrElse { GetOverallInfoEvent.Error(it) }
    }
}