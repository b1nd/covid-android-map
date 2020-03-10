package ru.b1nd.map.presentation.view.fragment

import org.koin.android.scope.currentScope
import ru.b1nd.map.R
import ru.b1nd.map.presentation.view.viewmodel.MapViewModel
import ru.b1nd.navigation.ui.BaseFragment

class MapFragment : BaseFragment() {

    private val model: MapViewModel by currentScope.inject()

    override val layoutRes: Int
        get() = R.layout.fragment_map

    override val shouldShowNavigationBar: Boolean
        get() = true
}