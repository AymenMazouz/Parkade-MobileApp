package com.example.parkingdev01.data.repository

import android.annotation.SuppressLint
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.data.remote.RetrofitInstance
import com.example.parkingdev01.util.ParkingResponse
import com.example.parkingdev01.util.ReservationResponse
import com.example.parkingdev01.util.TimeUtil
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException
import java.io.IOException

class ReservationRepository() {

//    private val reservationDao: ReservationDao
    private val reservationApi = RetrofitInstance.reservationApi

    // Remote:
    @SuppressLint("SuspiciousIndentation")
    suspend fun getAll(): List<Reservation> {
        try {
            val response = reservationApi.getAll()
            if (response.isSuccessful) {
                val jsonResponse = response.body()?.string()
                println("JSON Response: $jsonResponse") // Print JSON response for debugging


                jsonResponse?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


                    val jsonAdapter: JsonAdapter<ReservationResponse> =
                        moshi.adapter(ReservationResponse::class.java)
                    val reservationResponse = jsonAdapter.fromJson(jsonResponse)

                   val reservationList = reservationResponse?.items?.map { reservationItem ->
                     Reservation(
                           id = reservationItem.id,
                           parkingId = reservationItem.parking_id,
                           userId = reservationItem.user_id,
                           placedAt = reservationItem.placed_at,
                           entryTime = reservationItem.entry_time,
                           exitTime = reservationItem.exit_time,
                           price = reservationItem.price
                     ) } ?: emptyList()

                          return reservationList

                }

                return emptyList()

            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw e
        }
    }



    suspend fun getByUserId(id: Int): List<Reservation> {
        return try {
            val response = reservationApi.getByUserId(id)
            if (response.isSuccessful) {
                val jsonResponse = response.body()?.string()
                println("JSON Response: $jsonResponse") // Print JSON response for debugging

                jsonResponse?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter: JsonAdapter<ReservationResponse> = moshi.adapter(ReservationResponse::class.java)
                    val reservationResponse = jsonAdapter.fromJson(jsonResponse)

                    return reservationResponse?.items?.map { reservationItem ->
                        Reservation(
                            id = reservationItem.id,
                            parkingId = reservationItem.parking_id,
                            userId = reservationItem.user_id,
                            placedAt = reservationItem.placed_at,
                            entryTime = reservationItem.entry_time,
                            exitTime = reservationItem.exit_time,
                            price = reservationItem.price
                        )
                    } ?: emptyList()
                } ?: emptyList() // Return empty list if jsonResponse is null

            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw e
        }
    }







    suspend fun getById(id: Int): Reservation? {
        try {
            val response = reservationApi.getById(1)
            if (response.isSuccessful) {
                val jsonResponse = response.body()?.string()
                println("JSON Response: $jsonResponse") // Print JSON response for debugging

                return jsonResponse?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

                    // Use ParkingResponse to parse the JSON response
                    val jsonAdapter: JsonAdapter<ReservationResponse> = moshi.adapter(ReservationResponse::class.java)
                    val reservationResponse = jsonAdapter.fromJson(jsonResponse)

                    // Extract the single parking item from the response
                    reservationResponse?.items?.firstOrNull()?.let { reservationItem ->
                        Reservation(
                            id = reservationItem.id,
                            parkingId = reservationItem.parking_id,
                            userId = reservationItem.user_id,
                            placedAt = reservationItem.placed_at,
                            entryTime = reservationItem.entry_time,
                            exitTime = reservationItem.exit_time,
                            price = reservationItem.price
                        )
                    }
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw e
        }
        return null
    }























































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