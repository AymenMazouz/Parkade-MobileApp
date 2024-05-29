package com.example.parkingdev01.ui.screens.dashboard

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import com.example.parkingdev01.ui.viewmodels.ReservationViewModel
import com.example.parkingdev01.util.PreferencesManager
import kotlin.math.acos

@Composable
fun DashboardScreen(
    parkingViewModel: ParkingViewModel,
    reservationViewModel: ReservationViewModel,
    preferencesManager: PreferencesManager,
    activity: ComponentActivity
) {

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
            preferencesManager = preferencesManager,
            activity = activity,

            Modifier
                .padding(innerPadding)
                .padding(10.dp)
        )
    }
}
