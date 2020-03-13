package ru.b1nd.map.api.domain.entity

data class GeoInfo(
    val latitude: Double,
    val longitude: Double,
    val state: String?,
    val country: String,
    val infected: Long,
    val deaths: Long,
    val recovered: Long
)