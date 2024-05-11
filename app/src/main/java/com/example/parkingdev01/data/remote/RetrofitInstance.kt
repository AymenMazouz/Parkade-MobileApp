
package com.example.parkingdev01.data.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://7cfe-105-235-132-145.ngrok-free.app/ords/parkade/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()



    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }
}
