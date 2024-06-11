package com.example.parkingdev01.ui.viewmodels

import com.example.parkingdev01.data.repository.ReservationRepository
import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.model.Reservation


class ReservationViewModel(private val reservationRepository: ReservationRepository) : ViewModel() {

    suspend fun loadReservationRemote(): List<Reservation> {
        val reservations = reservationRepository.getAll()

        return reservations
    }

    suspend fun loadReservationByUser(id: Int): List<Reservation> {
        val parking = reservationRepository.getByUserId(id)
        return parking
    }

    suspend fun loadReservationDetails(id: Int): Reservation? {
        val parking = reservationRepository.getById(id)

        return parking
    }


    // Remote:
    suspend fun bookParkingSpace(reservation: Reservation): Boolean {
        return reservationRepository.book(reservation)
    }


}