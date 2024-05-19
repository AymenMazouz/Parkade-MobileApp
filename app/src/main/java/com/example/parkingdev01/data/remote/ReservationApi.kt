package com.example.parkingdev01.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface ReservationApi {

    @POST("reservation/book")
    suspend fun book(
        @Header("entryTime") entryTime: String,
        @Header("exitTime") exitTime: String,
        @Header("parkingId") parkingId: Int,
        @Header("userId") userId: Int,
        @Header("price") price: Long
        ): Response<ResponseBody>


}