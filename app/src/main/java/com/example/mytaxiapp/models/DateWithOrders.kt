package com.example.mytaxiapp.models

data class DateWithOrders(
    val date:String = "6 Июля, Вторник",
    val ordersInDate:List<Order>
)
