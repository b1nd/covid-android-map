package ru.b1nd.map.api.domain.usecase

import ru.b1nd.map.api.domain.entity.GeoInfo

interface GetGeoInfoUseCase {
    sealed class GetGeoInfoEvent {
        data class Success(val geoInfoList: List<GeoInfo>) : GetGeoInfoEvent()
        data class Error(val throwable: Throwable) : GetGeoInfoEvent()
        object Empty : GetGeoInfoEvent()
    }

    suspend fun execute(): GetGeoInfoEvent
}