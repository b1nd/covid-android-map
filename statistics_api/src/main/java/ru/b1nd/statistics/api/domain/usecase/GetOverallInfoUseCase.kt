package ru.b1nd.statistics.api.domain.usecase

import ru.b1nd.statistics.api.domain.entity.OverallInfo

interface GetOverallInfoUseCase {
    sealed class GetOverallInfoEvent {
        data class Success(val overallInfo: OverallInfo) : GetOverallInfoEvent()
        data class Error(val throwable: Throwable) : GetOverallInfoEvent()
        object Empty : GetOverallInfoEvent()
    }

    suspend fun execute(): GetOverallInfoEvent
}