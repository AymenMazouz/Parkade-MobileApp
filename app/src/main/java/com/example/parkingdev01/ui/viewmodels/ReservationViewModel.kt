package com.example.parkingdev01.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.data.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow



class ReservationViewModel(private val reservationRepository: ReservationRepository) : ViewModel() {

    suspend fun loadReservationRemote() : List<Reservation>{
       return reservationRepository.getAll()
    }
    suspend fun loadReservationByUser(id:Int) : List<Reservation>{
        val parking = reservationRepository.getByUserId(id)

        return parking
    }
    suspend fun loadReservationDetails(id:Int) : Reservation?{
        val parking = reservationRepository.getById(id)

        return parking
    }


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