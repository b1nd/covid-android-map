package ru.b1nd.near.presentation.view.fragment

import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.scope.currentScope
import ru.b1nd.navigation.ui.BaseFragment
import ru.b1nd.near.R
import ru.b1nd.near.domain.entity.UserLocationInfo
import ru.b1nd.near.presentation.viewmodel.NearViewModel
import ru.b1nd.near.presentation.viewmodel.NearViewState
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit

class NearFragment : BaseFragment() {
    private val model: NearViewModel by currentScope.inject()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var geocoder: Geocoder

    override val layoutRes: Int
        get() = R.layout.fragment_near

    override val shouldShowNavigationBar: Boolean
        get() = true

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geocoder = Geocoder(requireContext(), Locale.ENGLISH)
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(requireActivity())
        locationRequest = LocationRequest().apply {
            this.interval = UPDATE_INTERVAL_IN_MILLISECONDS
            this.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            this.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                if (locationResult != null && locationResult.lastLocation != null) {
                    val location: Location = locationResult.lastLocation

                    Log.d(TAG, "Received new location: $location")

                    try {
                        val address = geocoder
                            .getFromLocation(location.latitude, location.longitude, 1)
                            .first()
                        Log.d(TAG, "Got address: $address")

                        val userLocationInfo = UserLocationInfo(
                            location.latitude,
                            location.longitude,
                            address.locality,
                            address.adminArea,
                            address.countryCode,
                            address.countryName
                        )
                        model.onUserLocationChanged(userLocationInfo)
                    } catch (e: Exception) {
                        Log.e(TAG, e.message, e)
                    }
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
        lifecycleScope.launch {
            model.state
                .collect { updateState(it) }
        }
    }

    private fun updateState(state: NearViewState) {
        // todo: update ui
        Log.d(TAG, state.toString())
    }

    override fun onDestroyView() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onDestroyView()
    }

    companion object {
        private val TAG = NearFragment::class.java.simpleName
        private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = TimeUnit.SECONDS.toMillis(10)
        private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }
}