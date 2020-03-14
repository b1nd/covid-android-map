package ru.b1nd.near.api.domain.entity

data class CovidInfo(
    val infected: Long,
    val deaths: Long,
    val recovered: Long
)