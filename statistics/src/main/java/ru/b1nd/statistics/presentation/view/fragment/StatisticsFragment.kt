package ru.b1nd.statistics.presentation.view.fragment

import org.koin.android.scope.currentScope
import ru.b1nd.navigation.ui.BaseFragment
import ru.b1nd.statistics.R
import ru.b1nd.statistics.presentation.view.viewmodel.StatisticsViewModel

class StatisticsFragment : BaseFragment() {

    private val model: StatisticsViewModel by currentScope.inject()

    override val layoutRes: Int
        get() = R.layout.fragment_statistics

    override val shouldShowNavigationBar: Boolean
        get() = true
}