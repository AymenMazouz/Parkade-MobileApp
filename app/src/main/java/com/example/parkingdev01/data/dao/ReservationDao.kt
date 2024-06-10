package com.example.parkingdev01.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.parkingdev01.data.model.Reservation
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {

    @Query("SELECT * FROM reservation")
    fun getAllReservations(): List<Reservation>

    @Query("SELECT * FROM reservation WHERE userId = :userId")
    suspend fun getReservationsByUserId(userId: Int): List<Reservation>

    @Query("SELECT * FROM reservation WHERE id = :reservationId")
    suspend fun getReservationById(reservationId: Int): Reservation?

    @Query("DELETE FROM reservation")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reservations: List<Reservation>)
}
