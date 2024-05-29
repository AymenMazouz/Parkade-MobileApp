package com.example.parkingdev01.ui.screens.dashboard.map

import MapScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel

@Composable
fun MapContent(navController: NavController, parkingViewModel: ParkingViewModel) {
    var parkingList by remember { mutableStateOf(emptyList<Parking>()) }

    // Use LaunchedEffect to call the suspend function loadParkingRemote
    LaunchedEffect(Unit) {
        val loadedParkingList = parkingViewModel.loadParkingRemote()
        parkingList = loadedParkingList
    }

    MapScreen(navController = navController, parkingList = parkingList)
}
