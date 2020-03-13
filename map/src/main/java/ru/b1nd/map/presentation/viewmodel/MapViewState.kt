package ru.b1nd.map.presentation.viewmodel

import ru.b1nd.map.api.domain.entity.GeoInfo

data class MapViewState(
    val geoInfoList: List<GeoInfo> = listOf()
)