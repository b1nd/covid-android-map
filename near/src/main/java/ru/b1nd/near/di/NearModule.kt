package ru.b1nd.near.di

import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.experimental.builder.scopedBy
import ru.b1nd.near.api.domain.usecase.FindCovidInfoUseCase
import ru.b1nd.near.domain.usecase.FindCovidInfoUseCaseImpl
import ru.b1nd.near.presentation.view.fragment.NearFragment
import ru.b1nd.near.presentation.viewmodel.NearViewModel

@InternalCoroutinesApi
val nearModule = module {
    scope(named<NearFragment>()) {
        viewModel { NearViewModel(get()) }
        scopedBy<FindCovidInfoUseCase, FindCovidInfoUseCaseImpl>()
    }
}