package com.example.parkingdev01.data.repository

import com.example.parkingdev01.data.dao.ParkingDao
import com.example.parkingdev01.data.model.Parking
import kotlinx.coroutines.flow.Flow

class ParkingRepository(private val parkingDao: ParkingDao) {
    val allParkings: Flow<List<Parking>> = parkingDao.getAllParkings()
}