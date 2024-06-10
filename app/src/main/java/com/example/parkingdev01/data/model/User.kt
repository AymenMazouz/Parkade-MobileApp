package com.example.parkingdev01.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class User(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val firstName : String,
    val lastName : String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val photoUrl : String
)