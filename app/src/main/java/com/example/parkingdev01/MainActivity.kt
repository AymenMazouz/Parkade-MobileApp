package com.example.parkingdev01

import com.example.parkingdev01.ui.screens.dashboard.DashboardScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parkingdev01.data.repository.ParkingRepository
import com.example.parkingdev01.data.repository.ReservationRepository
import com.example.parkingdev01.data.repository.UserRepository
import com.example.parkingdev01.ui.screens.Destination
import com.example.parkingdev01.ui.screens.login.LoginScreen
import com.example.parkingdev01.ui.screens.login.SignUpScreen
import com.example.parkingdev01.ui.theme.ParkingDev01Theme
import com.example.parkingdev01.ui.viewmodels.AuthViewModel
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import com.example.parkingdev01.ui.viewmodels.ReservationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    private val userRepository by lazy { UserRepository() }
    private val authViewModel: AuthViewModel = AuthViewModel(userRepository)

    private val parkingRepository by lazy { ParkingRepository() }
    private val parkingViewModel: ParkingViewModel = ParkingViewModel(parkingRepository)

    private val reservationRepository by lazy { ReservationRepository() }
    private val reservationViewModel: ReservationViewModel = ReservationViewModel(reservationRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkingDev01Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()


                    ParkingAppNavigation(
                        navController = navController,
                        authViewModel,
                        parkingViewModel,
                        reservationViewModel
                    )
                }
            }
        }
    }
}


@Composable
fun ParkingAppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    parkingViewModel: ParkingViewModel,
    reservationViewModel: ReservationViewModel
) {


    NavHost(navController, startDestination = Destination.Dashboard.route) {

        // Login & Sign Up Destinations
        composable(Destination.Login.route) {
            LoginScreen(navController, authViewModel)
        }

        composable(Destination.SignUp.route) {
            SignUpScreen(navController, authViewModel)
        }

        // Dashboard Destination
        composable(Destination.Dashboard.route) {
            DashboardScreen(parkingViewModel, reservationViewModel = reservationViewModel)
        }


    }
}


