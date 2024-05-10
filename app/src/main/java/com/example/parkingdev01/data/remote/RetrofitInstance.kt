
package com.example.parkingdev01.data.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    public const val BASE_URL = "https://9396-105-235-132-225.ngrok-free.app/ords/parkade/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()



    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }
}
