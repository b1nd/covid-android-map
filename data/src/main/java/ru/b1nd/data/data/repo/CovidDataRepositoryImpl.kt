package ru.b1nd.data.data.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.apache.commons.csv.CSVFormat
import retrofit2.Retrofit
import ru.b1nd.data.api.data.repo.CovidDataRepository
import ru.b1nd.data.api.domain.entity.CovidDataRow
import ru.b1nd.data.data.framework.RetrofitCovidDataApi
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class CovidDataRepositoryImpl(
    private val retrofit: Retrofit
) : CovidDataRepository {
    private val repoUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/"

    private val covidDataApi = retrofit.newBuilder()
        .baseUrl(repoUrl)
        .build()
        .create(RetrofitCovidDataApi::class.java)

    private val mutex = Mutex()

    private var covidDataRows: List<CovidDataRow> = listOf()
    private var isDataLoaded: Boolean = false

    override suspend fun isDataLoaded(): Boolean  = mutex.withLock {
        isDataLoaded
    }

    override suspend fun loadData(date: LocalDate) = withContext(Dispatchers.IO) {
        val stream = covidDataApi.covidDailyData("${date.toRepoDateFormat()}.csv").byteStream()

        val parser = CSVFormat
            .newFormat(',')
            .parse(InputStreamReader(stream, "UTF8"))

        val recordsWithoutHeader = parser.records.drop(1)

        val covidRows = recordsWithoutHeader.map { record ->
            CovidDataRow(
                record.get(0).ifEmpty { null },
                record.get(1),
                LocalDateTime.parse(record.get(2)),
                record.get(3).toLong(),
                record.get(4).toLong(),
                record.get(5).toLong(),
                record.get(6).toDouble(),
                record.get(7).toDouble()
            )
        }
        mutex.withLock {
            covidDataRows = covidRows
            isDataLoaded = true
        }
    }

    override suspend fun getData(): Flow<CovidDataRow> = mutex.withLock {
        covidDataRows.asFlow()
    }

    private fun LocalDate.toRepoDateFormat(): String {
        return this.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
    }
}