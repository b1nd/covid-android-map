package ru.b1nd.presentation.view.activity

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.doOnApplyWindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import ru.b1nd.R
import ru.b1nd.data.api.data.repo.CovidDataRepository
import ru.b1nd.navigation.ui.BaseFragment
import ru.b1nd.presentation.viewmodel.AppViewModel
import ru.b1nd.presentation.viewmodel.AppViewState
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import java.lang.Exception
import java.time.LocalDate

class AppActivity : AppCompatActivity() {
    private val navigatorHolder: NavigatorHolder by inject()
    private val model: AppViewModel by inject()
    private val covidDataRepository: CovidDataRepository by inject()
    private val lifecycleCallbacks by lazy {
        LifecycleCallbacks()
    }

    private val navigator: Navigator =
        object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {
            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // fixme: I'm bad
        GlobalScope.launch(Dispatchers.IO) {
            loadCovidRepositoryData()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(lifecycleCallbacks, true)

        findViewById<View>(R.id.container).doOnApplyWindowInsets { view, insets, initialPadding ->
            view.updatePadding(
                left = initialPadding.left + insets.systemWindowInsetLeft,
                right = initialPadding.right + insets.systemWindowInsetRight
            )
            insets.replaceSystemWindowInsets(
                Rect(
                    0,
                    insets.systemWindowInsetTop,
                    0,
                    insets.systemWindowInsetBottom
                )
            )
        }
        setupBottomNavigation()

        GlobalScope.launch(Dispatchers.Main) {
            model.state
                .collect { updateState(it) }
        }
        model.onNearNavigation()
    }

    private fun setupBottomNavigation() {
        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_map -> model.onMapNavigation()
                    R.id.action_near -> model.onNearNavigation()
                    R.id.action_statistics -> model.onStatisticsNavigation()
                }
                true
            }
    }

    private fun updateState(state: AppViewState) {
        findViewById<View>(R.id.bottom_navigation).visibility =
            if (state.shouldShowBottomNavigation) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    private suspend fun loadCovidRepositoryData(
        date: LocalDate = LocalDate.now().minusDays(1),
        tries: Int = 5
    ) {
        try {
            covidDataRepository.loadData(date)
            Log.i(TAG, "Covid data loaded on date: $date")
        } catch (e: Exception) {
            if (tries > 0) {
                Log.w(TAG, "Cannot load covid data on date: $date", e)
                loadCovidRepositoryData(date.minusDays(1), tries - 1)
            } else {
                Log.e(TAG, e.message, e)
                throw e
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        model.onBack()
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(lifecycleCallbacks)
        super.onDestroy()
    }

    inner class LifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            super.onFragmentResumed(fm, f)
            val fragment = (f as? BaseFragment)
                ?: return
            val shouldShow = fragment.shouldShowNavigationBar
            model.onScreenShown(shouldShow)
        }
    }

    companion object {
        private val TAG = AppActivity::class.java.simpleName
    }
}