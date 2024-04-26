package com.example.parkingdev01.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.data.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow



class ReservationViewModel(private val repository: ReservationRepository) : ViewModel() {
    fun getUserReservations(userId: String): Flow<List<Reservation>> =
        repository.getUserReservations(userId)

    suspend fun insertReservation(reservation: Reservation) {
        repository.insertReservation(reservation)
    }

    suspend fun deleteReservation(reservation: Reservation) {
        repository.deleteReservation(reservation)
    }

    suspend fun clearReservations() {
        repository.clearReservations()
    }

}