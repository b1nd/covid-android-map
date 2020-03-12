package ru.b1nd.statistics.api.domain.entity

data class OverallInfo(
    val totalInfected: Long,
    val totalDeaths: Long,
    val totalRecovered: Long
)