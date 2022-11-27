package com.example.mytaxiapp.models

data class Order(
    val departurePoint: String = "Яшнабадский район, улица Sharof Rashidov, Ташкент",
    val destination: String = "Юнусабадский район, м-в юнусабад-19, ул. дехканабад, 17",
    val time: String = "19:24",
    val priceOfRide: String = "12 900 cум",
    val rideType: Int
)
