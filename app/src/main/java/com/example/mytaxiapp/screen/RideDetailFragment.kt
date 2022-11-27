package com.example.mytaxiapp.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.mytaxiapp.R
import com.example.mytaxiapp.databinding.FragmentRideDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class RideDetailFragment : Fragment(R.layout.fragment_ride_detail) {

    private lateinit var binding: FragmentRideDetailBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var bottomSheet: View


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRideDetailBinding.bind(view)

        initViews()
    }

    private fun initViews() {

        bottomSheet = binding.layoutRideDetails.root

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        getUserOrderHistory()

        binding.layoutRideDetails.btnDelete.setOnClickListener {

        }
    }

    private fun getUserOrderHistory() {
        
    }
}