package ru.b1nd

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.core.context.startKoin
import ru.b1nd.di.routerModule
import ru.b1nd.map.di.mapModule
import ru.b1nd.navigation.di.navigationModule
import ru.b1nd.near.di.nearModule
import ru.b1nd.statistics.di.statisticsModule

class App : Application() {

    @InternalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            modules(
                listOf(
                    navigationModule,
                    routerModule,
                    mapModule,
                    nearModule,
                    statisticsModule
                )
            )
        }
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
    }
}