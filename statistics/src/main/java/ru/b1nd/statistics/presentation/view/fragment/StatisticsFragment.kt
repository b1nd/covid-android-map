package ru.b1nd.statistics.presentation.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.scope.currentScope
import ru.b1nd.navigation.ui.BaseFragment
import ru.b1nd.statistics.R
import ru.b1nd.statistics.presentation.view.adapter.*
import ru.b1nd.statistics.presentation.viewmodel.*

class StatisticsFragment : BaseFragment() {

    private val model: StatisticsViewModel by currentScope.inject()

    private lateinit var statisticsOverallInfoAdapter: StatisticsOverallInfoAdapter
    private lateinit var statisticsCountriesInfoAdapter: StatisticsCountriesInfoAdapter

    override val layoutRes: Int
        get() = R.layout.fragment_statistics

    override val shouldShowNavigationBar: Boolean
        get() = true

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statisticsOverallInfoAdapter = StatisticsOverallInfoAdapter()
        overall_info_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = statisticsOverallInfoAdapter
        }

        statisticsCountriesInfoAdapter = StatisticsCountriesInfoAdapter()
        country_info_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = statisticsCountriesInfoAdapter
        }

        lifecycleScope.launch {
            model.state
                .collect { updateState(it) }
        }
        model.onInfoRequired()
    }

    private fun updateState(state: StatisticsViewState) {
        statisticsOverallInfoAdapter.items = listOf(
            StatisticsOverallInfoData(
                getString(R.string.overall_info_item_infected),
                state.overallInfo.totalInfected.toString()
            ),
            StatisticsOverallInfoData(
                getString(R.string.overall_info_item_deaths),
                state.overallInfo.totalDeaths.toString()
            ),
            StatisticsOverallInfoData(
                getString(R.string.overall_info_item_recovered),
                state.overallInfo.totalRecovered.toString()
            )
        )
        statisticsCountriesInfoAdapter.items = state.countriesInfo.map {
            StatisticsCountriesInfoData(
                it.country,
                it.infected.toString(),
                it.deaths.toString(),
                it.recovered.toString()
            )
        }
    }
}