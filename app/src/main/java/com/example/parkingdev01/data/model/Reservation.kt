package com.example.parkingdev01.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Reservation",
    foreignKeys = [ForeignKey(
        entity = Parking::class,
        parentColumns = ["id"],
        childColumns = ["parkingId"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class Reservation(
    @PrimaryKey val id: Int,
    val parkingId: Int,
    val userId: String,
    val date: String,
    val entryTime: String,
    val exitTime: String,
    val price: Double
)