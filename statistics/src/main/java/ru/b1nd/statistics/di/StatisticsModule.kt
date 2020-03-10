package ru.b1nd.statistics.di

import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.b1nd.statistics.presentation.view.fragment.StatisticsFragment
import ru.b1nd.statistics.presentation.view.viewmodel.StatisticsViewModel

@InternalCoroutinesApi
val statisticsModule = module {
    scope(named<StatisticsFragment>()) {
        viewModel { StatisticsViewModel() }
    }
}