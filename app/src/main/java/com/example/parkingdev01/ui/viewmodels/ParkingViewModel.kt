package com.example.parkingdev01.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.repository.ParkingRepository

class ParkingViewModel(private val parkingRepository: ParkingRepository) : ViewModel() {


    // Remote:
    suspend fun loadParkingRemote() : List<Parking>{
        return parkingRepository.getAll()
    }
    suspend fun loadParkingDetails(id:Int) : Parking?{
        val parking = parkingRepository.getById(id)

        return parking
    }
    // Local:
//    val allParkings: Flow<List<Parking>> = parkingRepository.allParkings
}
