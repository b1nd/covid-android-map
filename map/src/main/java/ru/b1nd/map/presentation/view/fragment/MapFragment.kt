package ru.b1nd.map.presentation.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_marker.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.scope.currentScope
import ru.b1nd.map.R
import ru.b1nd.map.presentation.viewmodel.MapViewModel
import ru.b1nd.map.presentation.viewmodel.MapViewState
import ru.b1nd.navigation.ui.BaseFragment


class MapFragment : BaseFragment(), OnMapReadyCallback {

    private val model: MapViewModel by currentScope.inject()

    private lateinit var map: GoogleMap

    override val layoutRes: Int
        get() = R.layout.fragment_map

    override val shouldShowNavigationBar: Boolean
        get() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        model.onGeoInfoRequired()
    }

    @ExperimentalCoroutinesApi
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setInfoWindowAdapter(object : InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null
            }
            @SuppressLint("InflateParams")
            override fun getInfoContents(marker: Marker): View {
                val context = requireContext()
                val inflater = LayoutInflater.from(context)
                val view = inflater.inflate(R.layout.map_marker, null, false)
                view.map_marker_title.text = marker.title
                view.map_marker_snippet.text = marker.snippet

                return view
            }
        })
        lifecycleScope.launch {
            model.state
                .collect { updateState(it) }
        }
        enableMyLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> when {
                grantResults.contains(PackageManager.PERMISSION_GRANTED) -> enableMyLocation()
            }
        }
    }

    private fun updateState(state: MapViewState) {
        map.clear()
        state.geoInfoList.forEach {
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(it.latitude, it.longitude))
                    .title(it.state ?: it.country)
                    .snippet("""
                        ${getString(R.string.marker_snippet_infected)} ${it.infected}
                        ${getString(R.string.marker_snippet_deaths)} ${it.deaths}
                        ${getString(R.string.marker_snippet_recovered)} ${it.recovered}
                    """.trimIndent())
            )
        }
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1
    }
}