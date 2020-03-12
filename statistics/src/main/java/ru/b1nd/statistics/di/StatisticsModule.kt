package ru.b1nd.statistics.di

import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.experimental.builder.scopedBy
import ru.b1nd.statistics.api.domain.usecase.GetCountriesInfoUseCase
import ru.b1nd.statistics.api.domain.usecase.GetOverallInfoUseCase
import ru.b1nd.statistics.domain.usecase.GetCountriesInfoUseCaseImpl
import ru.b1nd.statistics.domain.usecase.GetOverallInfoUseCaseImpl
import ru.b1nd.statistics.presentation.view.fragment.StatisticsFragment
import ru.b1nd.statistics.presentation.viewmodel.StatisticsViewModel

@InternalCoroutinesApi
val statisticsModule = module {
    scope(named<StatisticsFragment>()) {
        scopedBy<GetOverallInfoUseCase, GetOverallInfoUseCaseImpl>()
        scopedBy<GetCountriesInfoUseCase, GetCountriesInfoUseCaseImpl>()

        viewModel { StatisticsViewModel(get(), get()) }
    }
}