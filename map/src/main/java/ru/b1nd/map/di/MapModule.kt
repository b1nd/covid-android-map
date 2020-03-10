package ru.b1nd.map.di

import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.b1nd.map.presentation.view.fragment.MapFragment
import ru.b1nd.map.presentation.view.viewmodel.MapViewModel

@InternalCoroutinesApi
val mapModule = module {
    scope(named<MapFragment>()) {
        viewModel { MapViewModel() }
    }
}