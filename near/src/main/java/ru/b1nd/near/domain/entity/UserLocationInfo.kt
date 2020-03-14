package ru.b1nd.near.domain.entity

data class UserLocationInfo(
    val latitude: Double,
    val longitude: Double,
    val state: String,
    val admin: String,
    val countryCode: String,
    val country: String
)