package ru.b1nd.data.api.data.repo

import kotlinx.coroutines.flow.Flow
import ru.b1nd.data.api.domain.entity.CovidDataRow
import java.time.LocalDate

interface CovidDataRepository {

    suspend fun isDataLoaded(): Boolean

    suspend fun loadData(date: LocalDate)

    suspend fun getData(): Flow<CovidDataRow>
}