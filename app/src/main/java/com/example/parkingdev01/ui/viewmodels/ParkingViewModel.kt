package com.example.parkingdev01.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.repository.ParkingRepository
import kotlinx.coroutines.flow.Flow

class ParkingViewModel(private val repository: ParkingRepository) : ViewModel() {
    val allParkings: Flow<List<Parking>> = repository.allParkings
}
