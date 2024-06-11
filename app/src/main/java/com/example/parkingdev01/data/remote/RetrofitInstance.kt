package com.example.parkingdev01.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private const val NGROK_URL = "https://df91-41-111-189-173.ngrok-free.app/"
    private const val BASE_URL = NGROK_URL + "ords/parkade/"
    const val IMAGES_URL = NGROK_URL + "parking/"

    private const val SCHEDULER_URL = "https://1a14d65ee62c6b00bf1acf0f65faf385.loophole.site/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val schedulerRetrofit = Retrofit.Builder()
        .baseUrl(SCHEDULER_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    val parkingApi: ParkingApi by lazy {
        retrofit.create(ParkingApi::class.java)
    }

    val reservationApi: ReservationApi by lazy {
        retrofit.create(ReservationApi::class.java)
    }

    val notificationApi: NotificationApi by lazy {
        schedulerRetrofit.create(NotificationApi::class.java)
    }
}
