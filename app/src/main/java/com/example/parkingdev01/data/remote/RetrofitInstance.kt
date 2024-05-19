
package com.example.parkingdev01.data.remote

import com.example.parkingdev01.data.model.Reservation
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://c7c3-41-111-189-195.ngrok-free.app/ords/parkade/"
    public const val Images_URL ="https://c7c3-41-111-189-195.ngrok-free.app/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
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
}
