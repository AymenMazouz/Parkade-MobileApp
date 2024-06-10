package com.example.parkingdev01.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.parkingdev01.data.model.Parking
import kotlinx.coroutines.flow.Flow

@Dao
interface ParkingDao {
    @Query("SELECT * FROM Parking")
    fun getAll(): List<Parking>

    @Query("SELECT * FROM Parking WHERE id = :parkingId")
    fun getById(parkingId: Int): Parking

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(parkings: List<Parking>)

    @Query("DELETE FROM Parking")
    suspend fun clearAll()
}