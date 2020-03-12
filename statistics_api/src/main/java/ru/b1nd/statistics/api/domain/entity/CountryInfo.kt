package ru.b1nd.statistics.api.domain.entity

data class CountryInfo(
    val country: String,
    val infected: Long,
    val deaths: Long,
    val recovered: Long
)