package com.example.parkingdev01.data.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    public const val BASE_URL = "https://95a7-154-121-24-52.ngrok-free.app/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val dogApi: DogApi by lazy {
        retrofit.create(DogApi::class.java)
    }
}