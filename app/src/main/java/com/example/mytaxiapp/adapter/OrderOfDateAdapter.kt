package com.example.mytaxiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaxiapp.databinding.ItemLayoutOrderByDateBinding
import com.example.mytaxiapp.models.DateWithOrders
import com.example.mytaxiapp.models.Order

class OrderOfDateAdapter : ListAdapter<DateWithOrders, OrderOfDateAdapter.VH>(DiffUtil()) {

    lateinit var click: () -> Unit

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<DateWithOrders>() {
        override fun areItemsTheSame(oldItem: DateWithOrders, newItem: DateWithOrders): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DateWithOrders, newItem: DateWithOrders): Boolean {
            return oldItem == newItem
        }
    }

    class VH(val binding: ItemLayoutOrderByDateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(
            ItemLayoutOrderByDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            tvOrderDate.text = currentList[position].date
            refreshDateOrders(currentList[position].ordersInDate, rvDateOrders)
        }
    }

    private fun refreshDateOrders(ordersInDate: List<Order>, rvDateOrders: RecyclerView) {
        val adapter = OrderAdapter()
        adapter.submitList(ordersInDate)
        rvDateOrders.adapter = adapter
        adapter.click = {
            this.click()
        }
    }
}
