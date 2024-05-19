package com.example.parkingdev01.data.repository

import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.remote.RetrofitInstance
import com.example.parkingdev01.util.ParkingItem
import com.example.parkingdev01.util.ParkingResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException
import java.io.IOException

class ParkingRepository() {
    //    private val parkingDao: ParkingDao
    private val parkingApi = RetrofitInstance.parkingApi

    // Remote
    suspend fun getAll(): List<Parking> {
        try {
            val response = parkingApi.getAll()
            if (response.isSuccessful) {
                val jsonResponse = response.body()?.string()
                println("JSON Response: $jsonResponse") // Print JSON response for debugging


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

                return emptyList()

            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw e
        }
    }

    suspend fun getById(id: Int): Parking? {
        try {
            val response = parkingApi.getById(id)
            if (response.isSuccessful) {
                val jsonResponse = response.body()?.string()
                println("JSON Response: $jsonResponse") // Print JSON response for debugging

                return jsonResponse?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

                    // Use ParkingResponse to parse the JSON response
                    val jsonAdapter: JsonAdapter<ParkingResponse> = moshi.adapter(ParkingResponse::class.java)
                    val parkingResponse = jsonAdapter.fromJson(jsonResponse)

                    // Extract the single parking item from the response
                    parkingResponse?.items?.firstOrNull()?.let { parkingItem ->
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

    // Local:
    //    val allParkings: Flow<List<Parking>> = parkingDao.getAllParkings()
}