package ru.b1nd.data.api.domain.entity

import java.time.LocalDateTime

data class CovidDataRow(
    val state: String?,
    val country: String,
    val lastUpdate: LocalDateTime,
    val confirmed: Long,
    val deaths: Long,
    val recovered: Long,
    val latitude: Double,
    val longitude: Double
)