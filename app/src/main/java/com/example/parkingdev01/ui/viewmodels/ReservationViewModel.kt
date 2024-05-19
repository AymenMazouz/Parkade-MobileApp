package com.example.parkingdev01.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.data.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow



class ReservationViewModel(private val reservationRepository: ReservationRepository) : ViewModel() {


    // Remote:
    suspend fun bookParkingSpace(reservation: Reservation): Boolean {
        return reservationRepository.book(reservation)
    }




    // Local:
//    fun getUserReservations(userId: String): Flow<List<Reservation>> =
//        reservationRepository.getUserReservations(userId)
//
//    suspend fun insertReservation(reservation: Reservation) {
//        reservationRepository.insertReservation(reservation)
//    }
//
//    suspend fun deleteReservation(reservation: Reservation) {
//        reservationRepository.deleteReservation(reservation)
//    }
//
//    suspend fun clearReservations() {
//        reservationRepository.clearReservations()
//    }

}