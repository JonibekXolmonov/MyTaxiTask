package com.example.mytaxiapp.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.mytaxiapp.R
import com.example.mytaxiapp.adapter.OrderOfDateAdapter
import com.example.mytaxiapp.databinding.FragmentOrderHistoryBinding
import com.example.mytaxiapp.models.DateWithOrders
import com.example.mytaxiapp.models.Order
import com.example.mytaxiapp.utils.Constants.TYPE_STANDARD
import kotlin.random.Random

class OrderHistoryFragment : Fragment(R.layout.fragment_order_history) {

    private lateinit var binding: FragmentOrderHistoryBinding
    private val orderOfDateAdapter by lazy { OrderOfDateAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOrderHistoryBinding.bind(view)

        initViews()
    }

    private fun initViews() {
        refreshAdapter(generateData())
    }

    private fun generateData() = ArrayList<DateWithOrders>().apply {
        for (i in 0..3)
            this.add(DateWithOrders(ordersInDate = generateOrders()))
    }

    private fun generateOrders() = ArrayList<Order>().apply {
        for (i in 0..2)
            this.add(Order(rideType = Random.nextInt(0, 3)))
    }

    private fun refreshAdapter(orders: List<DateWithOrders>) {
        orderOfDateAdapter.submitList(orders)
        binding.rvOrders.adapter = orderOfDateAdapter

        orderOfDateAdapter.click = {
            findNavController().navigate(R.id.action_orderHistoryFragment_to_rideDetailFragment)
        }
    }
}