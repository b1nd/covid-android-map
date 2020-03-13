package ru.b1nd.map.presentation.viewmodel

import ru.b1nd.map.api.domain.entity.GeoInfo

typealias MapPartialViewState = (MapViewState) -> MapViewState

object MapPartialViewStates {

    fun onGeoInfoLoaded(geoInfoList: List<GeoInfo>): MapPartialViewState = {
        it.copy(
            geoInfoList = geoInfoList
        )
    }
}