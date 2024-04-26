package com.example.parkingdev01.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.parkingdev01.data.model.Parking
import kotlinx.coroutines.flow.Flow

@Dao
interface ParkingDao {
    @Query("SELECT * FROM Parking")
    fun getAllParkings(): Flow<List<Parking>>

    @Query("SELECT * FROM Parking WHERE id = :parkingId")
    fun getParkingById(parkingId: Int): Flow<Parking>
}