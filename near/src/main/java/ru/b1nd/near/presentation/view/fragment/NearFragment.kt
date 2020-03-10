package ru.b1nd.near.presentation.view.fragment

import org.koin.android.scope.currentScope
import ru.b1nd.navigation.ui.BaseFragment
import ru.b1nd.near.R
import ru.b1nd.near.presentation.view.viewmodel.NearViewModel

class NearFragment : BaseFragment() {

    private val model: NearViewModel by currentScope.inject()

    override val layoutRes: Int
        get() = R.layout.fragment_near

    override val shouldShowNavigationBar: Boolean
        get() = true
}