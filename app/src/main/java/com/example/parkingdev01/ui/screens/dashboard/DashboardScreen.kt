package com.example.parkingdev01.ui.screens.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parkingdev01.ui.screens.Destination

@Composable
fun DashboardScreen(navController: NavController) {


    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val navHostController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navHostController, startDestination = Destination.Map.route) {
            composable(Destination.Map.route) {
                MapScreen()
            }
            composable(Destination.Parkings.route) {
                ParkingContent()
            }
            composable(Destination.Reservations.route) {
                ReservationContent()
            }
            composable(Destination.Profile.route) {
                ProfileContent()
            }
        }

        DashboardBottomBar(
            navController = navController,
            onItemSelected = { index ->
                selectedItemIndex = index
            },
            selectedItemIndex = selectedItemIndex
        )
    }
}
