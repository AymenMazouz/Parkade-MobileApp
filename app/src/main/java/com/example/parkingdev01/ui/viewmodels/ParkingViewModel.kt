package com.example.parkingdev01.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.repository.ParkingRepository
import kotlinx.coroutines.flow.Flow

class ParkingViewModel(private val parkingRepository: ParkingRepository) : ViewModel() {


    // Remote:
    suspend fun loadParkingRemote() : List<Parking>{
        return parkingRepository.getAll()
    }
    suspend fun GetParkingById(id:Int) : Parking?{
        return parkingRepository.GetParkingById(id)
    }
    // Local:
//    val allParkings: Flow<List<Parking>> = parkingRepository.allParkings
}
