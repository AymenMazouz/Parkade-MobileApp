package com.example.parkingdev01.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Parking(
    @PrimaryKey val id: Int,
    val name: String,
    val location: String,
    val description: String,
    val photoUrl: Int,
    val commune: String,
    val wilaya: String,
    val longitude: Double,
    val latitude: Double

)