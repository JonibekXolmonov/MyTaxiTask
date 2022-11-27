package com.example.mytaxiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxiapp.R
import com.example.mytaxiapp.databinding.ItemLayoutOrderBinding
import com.example.mytaxiapp.models.Order
import com.example.mytaxiapp.utils.Constants.TYPE_BUSINESS
import com.example.mytaxiapp.utils.Constants.TYPE_LUGGAGE
import com.example.mytaxiapp.utils.Constants.TYPE_STANDARD

class OrderAdapter : ListAdapter<Order, OrderAdapter.VH>(DiffUtil()) {

    lateinit var click: () -> Unit

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    class VH(val binding: ItemLayoutOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        ItemLayoutOrderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            when (currentList[position].rideType) {
                TYPE_LUGGAGE -> ivRideType.setImageResource(R.drawable.ic_car_luggage)
                TYPE_STANDARD -> ivRideType.setImageResource(R.drawable.ic_car_standard)
                TYPE_BUSINESS -> ivRideType.setImageResource(R.drawable.ic_car_business)
            }

            root.setOnClickListener {
                click()
            }
        }
    }
}
