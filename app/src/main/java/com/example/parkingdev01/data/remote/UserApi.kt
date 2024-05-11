package com.example.parkingdev01.data.remote

import com.squareup.moshi.JsonClass
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {
    @GET("user/login")
    suspend fun authenticate(
        @Header("email") email: String,
        @Header("password") password: String
    ): Response<ResponseBody>



//    @Headers("Content-Type: application/json") // Specify the content type if needed
    @POST("user/signup")
    suspend fun signUp(
        @Header("email") email: String,
        @Header("password") password: String,
        @Header("firstName") firstName: String,
        @Header("lastName") lastName: String,
        @Header("phoneNumber") phoneNumber: String,
        @Header("photoUrl") photoUrl: String
    ): Response<ResponseBody>

}


