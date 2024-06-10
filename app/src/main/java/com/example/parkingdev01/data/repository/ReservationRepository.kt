package com.example.parkingdev01.data.repository

import android.content.Context
import com.example.parkingdev01.data.dao.ReservationDao
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.data.remote.ReservationApi
import com.example.parkingdev01.data.remote.RetrofitInstance
import com.example.parkingdev01.util.Constants
import com.example.parkingdev01.util.ReservationResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException
import java.io.IOException

class ReservationRepository(
    private val reservationDao: ReservationDao,
    private val context: Context // Pass the context to access system services

) {

    private val reservationApi: ReservationApi = RetrofitInstance.reservationApi
    suspend fun getAll(): List<Reservation> {
        return if (Constants.isOnline(context)) {
            val reservations = fetchFromRemote()
            reservationDao.clearAll()
            reservationDao.insertAll(reservations)
            reservations
        } else {
            reservationDao.getAllReservations()
        }
    }

    private suspend fun fetchFromRemote(): List<Reservation> {
        try {
            val response = reservationApi.getAll()
            if (response.isSuccessful) {
                val jsonResponse = response.body()?.string()
                jsonResponse?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter: JsonAdapter<ReservationResponse> =
                        moshi.adapter(ReservationResponse::class.java)
                    val reservationResponse = jsonAdapter.fromJson(jsonResponse)
                    return reservationResponse?.items?.map { reservationItem ->
                        Reservation(
                            id = reservationItem.id,
                            parkingId = reservationItem.parking_id,
                            userId = reservationItem.user_id,
                            placedAt = reservationItem.placed_at.toLong(),
                            entryTime = reservationItem.entry_time.toLong(),
                            exitTime = reservationItem.exit_time.toLong(),
                            price = reservationItem.price
                        )
                    } ?: emptyList()
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw e
        }
        return emptyList()
    }

    suspend fun getByUserId(id: Int): List<Reservation> {
        return if (Constants.isOnline(context)) {
            val reservations = fetchByUserIdRemote(id)
            reservationDao.clearAll()
            reservationDao.insertAll(reservations)
            reservations
        } else {
            reservationDao.getReservationsByUserId(id)
        }
    }

    private suspend fun fetchByUserIdRemote(id: Int): List<Reservation> {
        try {
            val response = reservationApi.getByUserId(id)
            if (response.isSuccessful) {
                val jsonResponse = response.body()?.string()
                jsonResponse?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter: JsonAdapter<ReservationResponse> =
                        moshi.adapter(ReservationResponse::class.java)
                    val reservationResponse = jsonAdapter.fromJson(jsonResponse)
                    return reservationResponse?.items?.map { reservationItem ->
                        Reservation(
                            id = reservationItem.id,
                            parkingId = reservationItem.parking_id,
                            userId = reservationItem.user_id,
                            placedAt = reservationItem.placed_at.toLong(),
                            entryTime = reservationItem.entry_time.toLong(),
                            exitTime = reservationItem.exit_time.toLong(),
                            price = reservationItem.price
                        )
                    } ?: emptyList()
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw e
        }
        return emptyList()

    }

    suspend fun getById(id: Int): Reservation? {
        return if (Constants.isOnline(context)) {
            val reservation = fetchByIdRemote(id)

            reservation
        } else {
            reservationDao.getReservationById(id)
        }
    }
    private suspend fun fetchByIdRemote(id: Int): Reservation? {
        try {
            val response = reservationApi.getById(id)
            if (response.isSuccessful) {
                val jsonResponse = response.body()?.string()
                jsonResponse?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter: JsonAdapter<ReservationResponse> =
                        moshi.adapter(ReservationResponse::class.java)
                    val reservationResponse = jsonAdapter.fromJson(jsonResponse)
                    return reservationResponse?.items?.firstOrNull()?.let { reservationItem ->
                        Reservation(
                            id = reservationItem.id,
                            parkingId = reservationItem.parking_id,
                            userId = reservationItem.user_id,
                            placedAt = reservationItem.placed_at.toLong(),
                            entryTime = reservationItem.entry_time.toLong(),
                            exitTime = reservationItem.exit_time.toLong(),
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
        val response = reservationApi.book(
            reservation.entryTime,
            reservation.exitTime,
            reservation.parkingId,
            reservation.userId,
            reservation.price
        )

        if (response.isSuccessful) {
            val jsonResponse = response.body()?.string()
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
}
