

package com.example.parkingdev01.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {
    @GET("user/login")
    suspend fun isUserExists(
        @Header("email") email: String,
        @Header("password") password: String
    ): Response<ResponseBody>
}
