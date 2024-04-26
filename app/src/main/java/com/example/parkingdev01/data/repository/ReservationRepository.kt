package com.example.parkingdev01.data.repository

import com.example.parkingdev01.data.dao.ReservationDao
import com.example.parkingdev01.data.model.Reservation
import kotlinx.coroutines.flow.Flow

class ReservationRepository(private val reservationDao: ReservationDao) {

    fun getUserReservations(userId: String): Flow<List<Reservation>> =
        reservationDao.getUserReservations(userId)

    suspend fun insertReservation(reservation: Reservation) {
        reservationDao.insertReservation(reservation)
    }

    suspend fun deleteReservation(reservation: Reservation) {
        reservationDao.deleteReservation(reservation)
    }

    suspend fun clearReservations() {
        reservationDao.clearReservations()
    }
}