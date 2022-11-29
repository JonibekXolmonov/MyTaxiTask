package com.example.mytaxiapp.screen

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.directions.route.*
import com.example.mytaxiapp.R
import com.example.mytaxiapp.databinding.FragmentRideDetailBinding
import com.example.mytaxiapp.utils.Constants.KEY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList

class RideDetailFragment : Fragment(R.layout.fragment_ride_detail), OnMapReadyCallback,
    RoutingListener {

    private lateinit var binding: FragmentRideDetailBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var bottomSheet: View
    private lateinit var mMap: GoogleMap
    private var polyLines: MutableList<Polyline>? = null
    private val departureLocation = LatLng(41.2925792, 69.2976831)
    private val arrivalDestination = LatLng(41.29457, 69.26788)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRideDetailBinding.bind(view)

        initViews()
    }

    private fun initViews() {

        bottomSheet = binding.layoutRideDetails.root

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        binding.layoutRideDetails.btnDelete.setOnClickListener {

        }

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.moveCamera(CameraUpdateFactory.newLatLng(departureLocation))
        googleMap.uiSettings.isZoomControlsEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        googleMap.uiSettings.isScrollGesturesEnabled = false
        googleMap.uiSettings.isZoomGesturesEnabled = false

        addMarker(departureLocation, R.drawable.ic_circle_from)
        addMarker(arrivalDestination, R.drawable.ic_circle_to)

        googleMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                    .target(getMiddlePoint(arrivalDestination, departureLocation)).zoom(12.4f)
                    .bearing(5f).tilt(45f).build()
            )
        )

        findRoutes(departureLocation, arrivalDestination)
    }

    private fun getMiddlePoint(arrivalDestination: LatLng, departureLocation: LatLng): LatLng {
        return LatLng(
            (arrivalDestination.latitude + departureLocation.latitude) / 2.0,
            (arrivalDestination.longitude + departureLocation.longitude) / 2.0,
        )
    }

    private fun addMarker(location: LatLng, marker: Int) {
        mMap.addMarker(
            MarkerOptions().position(
                LatLng(
                    location.latitude,
                    location.longitude
                )
            )
                .icon(bitmapFromVector(marker))
        )
    }

    private fun bitmapFromVector(vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
        vectorDrawable!!.setBounds(
            0, 0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onRoutingFailure(p0: RouteException?) {

    }

    override fun onRoutingStart() {

    }

    override fun onRoutingSuccess(route: ArrayList<Route>?, shortestRouteIndex: Int) {
        if (polyLines != null) {
            polyLines?.clear()
        }
        val polyOptions = PolylineOptions()
        polyLines = ArrayList()
        for (i in route!!.indices) {
            if (i == shortestRouteIndex) {
                polyOptions.color(
                    Color.parseColor("#5B89FF")
                )
                polyOptions.width(10f)
                polyOptions.addAll(route[shortestRouteIndex].points)
                val polyline = mMap.addPolyline(polyOptions)
                (polyLines as ArrayList<Polyline>).add(polyline)
            }
        }
    }

    override fun onRoutingCancelled() {

    }

    private fun findRoutes(start: LatLng?, end: LatLng?) {
        val routing = Routing.Builder()
            .travelMode(AbstractRouting.TravelMode.DRIVING)
            .withListener(this)
            .alternativeRoutes(true)
            .waypoints(start, end)
            .key(KEY)
            .build()
        routing.execute()
    }
}
