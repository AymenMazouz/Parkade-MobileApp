package com.example.parkingdev01.data.repository

import com.example.parkingdev01.data.dao.ReservationDao
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.data.remote.RetrofitInstance
import com.example.parkingdev01.util.TimeUtil
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow

class ReservationRepository() {

//    private val reservationDao: ReservationDao
    private val reservationApi = RetrofitInstance.reservationApi

    // Remote:


    suspend fun book(reservation: Reservation): Boolean {

        val entryTimeFormatted = TimeUtil.formatTimestamp(reservation.entryTime.toLong())
        val exitTimeFormatted = TimeUtil.formatTimestamp(reservation.exitTime.toLong())

        val response = reservationApi.book(
            entryTimeFormatted,
            exitTimeFormatted,
            reservation.parkingId,
            reservation.userId,
            reservation.price
        )

        if (response.isSuccessful) {
            val jsonResponse = response.body()?.string()
            println("JSON Response: $jsonResponse") // Print JSON response for debugging
            jsonResponse?.let {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val type = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
                val jsonAdapter: JsonAdapter<Map<String, String>> = moshi.adapter(type)
                val responseObject: Map<String, String>? = jsonAdapter.fromJson(jsonResponse)
                // Check if output_message is "success"
                return responseObject?.get("output_message") == "success"
            }
        }
        return false

    }


    // Local:
//    fun getUserReservations(userId: String): Flow<List<Reservation>> =
//        reservationDao.getUserReservations(userId)
//
//    suspend fun insertReservation(reservation: Reservation) {
//        reservationDao.insertReservation(reservation)
//    }
//
//    suspend fun deleteReservation(reservation: Reservation) {
//        reservationDao.deleteReservation(reservation)
//    }
//
//    suspend fun clearReservations() {
//        reservationDao.clearReservations()
//    }
}