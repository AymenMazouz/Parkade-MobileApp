package com.example.parkingdev01.util

import com.example.parkingdev01.data.model.Parking
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ReservationResponse(
    val items: List<ReservationItem>
)

data class ReservationItem(
    val id: Int,
    val parking_id:Int,
    val user_id:Int,
    val placed_at: String,
    val entry_time: String,
    val exit_time: String,
    val price: Long
)
