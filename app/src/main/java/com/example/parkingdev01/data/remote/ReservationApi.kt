package com.example.parkingdev01.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationApi {

    @GET("reservation/all")
    suspend fun getAll(): Response<ResponseBody>

    @GET("reservation/user/{id}")
    suspend fun getByUserId(
        @Path("id") id: Int
    ):Response<ResponseBody>

    @GET("reservation/{id}")
    suspend fun getById(
        @Path("id") id: Int
    ): Response<ResponseBody>





    @POST("reservation/book")
    suspend fun book(
        @Header("entryTime") entryTime: String,
        @Header("exitTime") exitTime: String,
        @Header("parkingId") parkingId: Int,
        @Header("userId") userId: Int,
        @Header("price") price: Long
        ): Response<ResponseBody>


}