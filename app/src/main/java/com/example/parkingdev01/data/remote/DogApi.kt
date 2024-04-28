package com.example.parkingdev01.data.remote

import com.example.parkingdev01.data.model.Dog
import retrofit2.http.GET

interface DogApi {
    @GET("dogs/getall")
    suspend fun getDogs(): List<Dog>
}
