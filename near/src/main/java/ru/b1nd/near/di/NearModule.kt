package ru.b1nd.near.di

import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.b1nd.near.presentation.view.fragment.NearFragment
import ru.b1nd.near.presentation.view.viewmodel.NearViewModel

@InternalCoroutinesApi
val nearModule = module {
    scope(named<NearFragment>()) {
        viewModel { NearViewModel() }
    }
}