package com.example.parkingdev01.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ParkingApi {
    @GET("parking/all")
    suspend fun getAll(): Response<ResponseBody>


    @GET("parking/{id}")
    suspend fun getById(
        @Path("id") id: Int
    ): Response<ResponseBody>

}