package com.example.mytaxiapp.screen

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mytaxiapp.R
import com.example.mytaxiapp.databinding.FragmentHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentHomeBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        initViews()
    }

    private fun initViews() {

//         Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.ivMenu.setOnClickListener {
            openDrawerLayout()
        }

        binding.ivLocateMe.setOnClickListener {
        }

        binding.tvMyOrders.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_orderHistoryFragment)
            closeDrawer()
        }

    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START, true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Bobur Park and move the camera
        val boburPark = LatLng(41.2901816305, 69.2562332295)
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.moveCamera(CameraUpdateFactory.newLatLng(boburPark))
        cameraMoveStartedListener(googleMap)
        googleMap.uiSettings.isZoomControlsEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                boburPark, 12.4f
            )
        )
    }

    private fun cameraMoveStartedListener(googleMap: GoogleMap) {

        googleMap.setOnCameraMoveStartedListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    binding.tvPinnedLocation.text = "Loading."
                    delay(400)
                    binding.tvPinnedLocation.text = "Loading.."
                    delay(400)
                    binding.tvPinnedLocation.text = "Loading..."
                }
            }
        }

        googleMap.setOnCameraIdleListener {
            val gcd = Geocoder(context, Locale.getDefault())
            val addresses: List<Address> = gcd.getFromLocation(
                googleMap.cameraPosition.target.latitude,
                googleMap.cameraPosition.target.longitude,
                1
            )
            if (addresses.isNotEmpty()) {
                binding.tvPinnedLocation.text = addresses[0].getAddressLine(0)
            } else {
                // do your stuff
            }
        }
    }


    private fun openDrawerLayout() {
        binding.drawerLayout.openDrawer(GravityCompat.START, true)
    }
}