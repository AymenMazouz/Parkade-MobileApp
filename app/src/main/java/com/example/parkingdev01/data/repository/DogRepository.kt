package com.example.parkingdev01.data.repository

import com.example.parkingdev01.data.model.Dog
import com.example.parkingdev01.data.remote.RetrofitInstance

class DogRepository {
    suspend fun getDogsFromRemote(): List<Dog> {
        val dogs =  RetrofitInstance.dogApi.getDogs()
        return dogs
    }
}