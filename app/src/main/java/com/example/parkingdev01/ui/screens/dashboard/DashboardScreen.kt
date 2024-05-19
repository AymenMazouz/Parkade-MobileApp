package com.example.parkingdev01.ui.screens.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import com.example.parkingdev01.ui.viewmodels.ReservationViewModel

@Composable
fun DashboardScreen(parkingViewModel: ParkingViewModel, reservationViewModel: ReservationViewModel) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            DashboardBottomBar(navController)
        }
    ) { innerPadding ->
        DashboardNavGraph(
            navController = navController,
            parkingViewModel,
            reservationViewModel = reservationViewModel,
            Modifier
                .padding(innerPadding)
                .padding(10.dp)
        )
    }
}
