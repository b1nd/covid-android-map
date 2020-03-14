package ru.b1nd.near.domain.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList
import ru.b1nd.data.api.data.repo.CovidDataRepository
import ru.b1nd.near.api.domain.entity.CovidInfo
import ru.b1nd.near.api.domain.usecase.FindCovidInfoUseCase
import ru.b1nd.near.api.domain.usecase.FindCovidInfoUseCase.FindCovidInfoEvent

class FindCovidInfoUseCaseImpl(
    private val covidDataRepository: CovidDataRepository
) : FindCovidInfoUseCase {
    override suspend fun execute(
        countryPredicate: suspend (String) -> Boolean,
        statePredicate: (suspend (String?) -> Boolean)?
    ) = coroutineScope {
        runCatching {
            if (covidDataRepository.isDataLoaded()) {
                val countryRows = covidDataRepository.getData()
                    .filter { countryPredicate(it.country) }
                    .filter { it.confirmed != 0L || it.deaths != 0L || it.recovered != 0L }

                val rows = if (statePredicate != null) {
                    countryRows.filter { statePredicate(it.state) }
                } else {
                    countryRows
                }.toList()

                if (rows.isEmpty()) {
                    FindCovidInfoEvent.Empty
                } else {
                    val infected  = async { rows.fold(0L) { acc, it -> acc + it.confirmed } }
                    val deaths    = async { rows.fold(0L) { acc, it -> acc + it.deaths } }
                    val recovered = async { rows.fold(0L) { acc, it -> acc + it.recovered } }

                    FindCovidInfoEvent.Success(
                        CovidInfo(
                            infected.await(),
                            deaths.await(),
                            recovered.await()
                        )
                    )
                }
            } else {
                FindCovidInfoEvent.Empty
            }
        }.getOrElse { FindCovidInfoEvent.Error(it) }
    }
}