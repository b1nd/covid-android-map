package ru.b1nd

import androidx.fragment.app.Fragment
import kotlinx.coroutines.InternalCoroutinesApi
import ru.b1nd.map.presentation.view.fragment.MapFragment
import ru.b1nd.near.presentation.view.fragment.NearFragment
import ru.b1nd.statistics.presentation.view.fragment.StatisticsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object Map: SupportAppScreen() {
        @InternalCoroutinesApi
        override fun getFragment(): Fragment = MapFragment()
    }

    object Near: SupportAppScreen() {
        @InternalCoroutinesApi
        override fun getFragment(): Fragment = NearFragment()
    }

    object Statistics: SupportAppScreen() {
        @InternalCoroutinesApi
        override fun getFragment(): Fragment = StatisticsFragment()
    }
}