package com.example.parkingdev01.util

import com.example.parkingdev01.data.model.Parking
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ParkingResponse(
    val items: List<ParkingItem>
)

data class ParkingItem(
    val id: Int,
    val name: String,
    val location: String,
    val photo_url: String,
    val commune: String,
    val wilaya: String,
    val longitude: Double,
    val latitude: Double,
    val daily_price: Double,
    val total_places: Int,
    val available_places: Int,
    val description: String
)
