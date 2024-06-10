package com.example.parkingdev01.ui.screens.dashboard

import ParkingContent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parkingdev01.ui.screens.Destination
import com.example.parkingdev01.ui.screens.dashboard.content.ProfileContent
import com.example.parkingdev01.ui.screens.dashboard.map.MapContent
import com.example.parkingdev01.ui.screens.dashboard.parking.ParkingDetails
import com.example.parkingdev01.ui.screens.dashboard.reservation.ReservationContent
import com.example.parkingdev01.ui.screens.dashboard.reservation.ReservationDetails
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import com.example.parkingdev01.ui.viewmodels.ReservationViewModel
import com.example.parkingdev01.util.PreferencesManager

@Composable
fun DashboardNavGraph(
    navController: NavHostController,
    parkingViewModel: ParkingViewModel,
    reservationViewModel: ReservationViewModel,
    preferencesManager: PreferencesManager,
    activity: ComponentActivity,
    modifier: Modifier
) {


    NavHost(navController = navController, startDestination = Destination.Map.route) {
        composable(Destination.Map.route) {
            MapContent(navController,parkingViewModel)
        }
        composable(Destination.Parkings.route) {
            ParkingContent(navController,preferencesManager,parkingViewModel)
        }
        composable(Destination.Reservations.route) {
            ReservationContent(navController,preferencesManager,reservationViewModel) //TO CHAAAAAAAAAAAAANGEEEEEEE
        }
        composable(Destination.Profile.route) {
            ProfileContent(navController, preferencesManager, activity = activity)
        }


        composable(Destination.ParkingDetails.route) { backStackEntry ->
            val parkingId = backStackEntry.arguments?.getString("parkingId")?.toIntOrNull()
            if (parkingId != null) {
                ParkingDetails(parkingId, parkingViewModel, reservationViewModel)
            }
        }
        composable(Destination.ReservationDetails.route) { backStackEntry ->
            val reservationId = backStackEntry.arguments?.getString("reservationId")?.toIntOrNull()
            if (reservationId != null) {
                ReservationDetails(reservationId , reservationViewModel)
            }
        }
    }

}