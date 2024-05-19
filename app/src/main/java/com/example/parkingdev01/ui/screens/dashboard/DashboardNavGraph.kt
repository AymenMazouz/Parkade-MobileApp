package com.example.parkingdev01.ui.screens.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parkingdev01.ui.screens.Destination
import com.example.parkingdev01.ui.screens.dashboard.map.MapContent
import com.example.parkingdev01.ui.screens.dashboard.parking.ParkingContent
import com.example.parkingdev01.ui.screens.dashboard.content.ProfileContent
import com.example.parkingdev01.ui.screens.dashboard.content.ReservationContent
import com.example.parkingdev01.ui.screens.dashboard.parking.ParkingDetails
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel

@Composable
fun DashboardNavGraph(
    navController: NavHostController,
    parkingViewModel: ParkingViewModel,
    modifier: Modifier
) {


    NavHost(navController = navController, startDestination = Destination.Map.route) {
        composable(Destination.Map.route) {
            MapContent()
        }
        composable(Destination.Parkings.route) {
            ParkingContent(navController,parkingViewModel)
        }
        composable(Destination.Reservations.route) {
            ReservationContent()
        }
        composable(Destination.Profile.route) {
            ProfileContent(navController)
        }


        composable(Destination.ParkingDetails.route) { backStackEntry ->
            val parkingId = backStackEntry.arguments?.getString("parkingId")?.toIntOrNull()
            if (parkingId != null) {
                ParkingDetails(parkingId, parkingViewModel)
            }
        }
    }

}