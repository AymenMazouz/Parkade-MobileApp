package com.example.parkingdev01.data.repository

import android.content.Context
import com.example.parkingdev01.data.dao.ParkingDao
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.remote.RetrofitInstance
import com.example.parkingdev01.util.Constants
import com.example.parkingdev01.util.ParkingResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ParkingRepository(private val parkingDao: ParkingDao, private val context: Context) {
    private val parkingApi = RetrofitInstance.parkingApi

    suspend fun getAll(): List<Parking> {
        return withContext(Dispatchers.IO) {
            if (Constants.isOnline(context)) {
                val parkings = fetchFromRemote()
                parkingDao.clearAll()
                parkingDao.insertAll(parkings)
                parkings
            } else {
                parkingDao.getAll()
            }
        }
    }

    private suspend fun fetchFromRemote(): List<Parking> {
        try {
            val response = parkingApi.getAll()
            if (response.isSuccessful) {
                val jsonResponse = response.body()?.string()
                jsonResponse?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter: JsonAdapter<ParkingResponse> =
                        moshi.adapter(ParkingResponse::class.java)
                    val parkingResponse = jsonAdapter.fromJson(jsonResponse)

                    val parkingList = parkingResponse?.items?.map { parkingItem ->
                        Parking(
                            id = parkingItem.id,
                            name = parkingItem.name,
                            location = parkingItem.location,
                            photoUrl = parkingItem.photo_url,
                            commune = parkingItem.commune,
                            wilaya = parkingItem.wilaya,
                            longitude = parkingItem.longitude,
                            latitude = parkingItem.latitude,
                            dailyPrice = parkingItem.daily_price,
                            totalPlaces = parkingItem.total_places,
                            availablePlaces = parkingItem.available_places,
                            description = parkingItem.description
                        )
                    } ?: emptyList()

                    return parkingList
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw e
        }
        return emptyList()
    }

    suspend fun getById(id: Int): Parking? {
        return withContext(Dispatchers.IO) {
            if (Constants.isOnline(context)) {
                fetchByIdRemote(id)
            } else {
                parkingDao.getById(id)
            }
        }
    }

    private suspend fun fetchByIdRemote(id: Int): Parking? {
        try {
            val response = parkingApi.getById(id)
            if (response.isSuccessful) {
                val jsonResponse = response.body()?.string()
                jsonResponse?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter: JsonAdapter<ParkingResponse> =
                        moshi.adapter(ParkingResponse::class.java)
                    val parkingResponse = jsonAdapter.fromJson(jsonResponse)

                    return parkingResponse?.items?.firstOrNull()?.let { parkingItem ->
                        Parking(
                            id = parkingItem.id,
                            name = parkingItem.name,
                            location = parkingItem.location,
                            photoUrl = parkingItem.photo_url,
                            commune = parkingItem.commune,
                            wilaya = parkingItem.wilaya,
                            longitude = parkingItem.longitude,
                            latitude = parkingItem.latitude,
                            dailyPrice = parkingItem.daily_price,
                            totalPlaces = parkingItem.total_places,
                            availablePlaces = parkingItem.available_places,
                            description = parkingItem.description
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
}
