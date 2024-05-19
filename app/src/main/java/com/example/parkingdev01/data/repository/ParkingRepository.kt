    package com.example.parkingdev01.data.repository

    import com.example.parkingdev01.data.model.Parking
    import com.example.parkingdev01.data.remote.RetrofitInstance
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


                        val jsonAdapter: JsonAdapter<ParkingResponse> = moshi.adapter(ParkingResponse::class.java)
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
                                availablePlaces = parkingItem.available_places
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

        suspend fun GetParkingById(id: Int): Parking? {
            try {
                val response = parkingApi.GetParkingById(id)
                if (response.isSuccessful) {
                    val jsonResponse = response.body()?.string()
                    println("JSON Response: $jsonResponse") // Print JSON response for debugging

                    return jsonResponse?.let {
                        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

                        val jsonAdapter: JsonAdapter<ParkingResponse> = moshi.adapter(ParkingResponse::class.java)
                        val parkingResponse = jsonAdapter.fromJson(jsonResponse)

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
                                availablePlaces = parkingItem.available_places
                            )
                        }
                    }
                } else {
                    throw HttpException(response)
                }
            } catch (e: IOException) {
                throw e
            }
        }

        // Local:
    //    val allParkings: Flow<List<Parking>> = parkingDao.getAllParkings()
    }