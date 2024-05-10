package com.example.parkingdev01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parkingdev01.data.repository.UserRepository
import com.example.parkingdev01.ui.screens.Destination
import com.example.parkingdev01.ui.screens.dashboard.MapScreen
import com.example.parkingdev01.ui.screens.login.LoginScreen
import com.example.parkingdev01.ui.theme.ParkingDev01Theme
import com.example.parkingdev01.ui.viewmodels.LoginViewModel

class MainActivity : ComponentActivity() {
//    private val dogRepository by lazy { DogRepository() }

//    private val dogViewModel: DogViewModel = DogViewModel(dogRepository)



    private val userRepository by lazy { UserRepository() }
    private val loginViewModel: LoginViewModel = LoginViewModel(userRepository)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkingDev01Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    ParkingAppNavigation(navController = navController, loginViewModel)
                }
            }
        }
    }
}





@Composable
fun ParkingAppNavigation(navController: NavHostController, loginViewModel: LoginViewModel) {

    NavHost(navController, startDestination = Destination.Login.route) {
        // Login & Sign Up Destination
        composable(Destination.Login.route) {
            LoginScreen(navController,loginViewModel)
        }
        composable(Destination.SignUp.route) {
//            SignUpScreen(navController)
        }

        // Dashboard Screen
        composable(Destination.Dashboard.route) {
//            DashboardScreen(navController)
        }

        // Map Screen
        composable(Destination.Map.route) {
            MapScreen()
        }

        // Parkings Screen
        composable(Destination.Parkings.route) {
//            ParkingsScreen()
        }

        // Reservations Screen
        composable(Destination.Reservations.route) {
//            ReservationsScreen()
        }

        // Profile Screen
        composable(Destination.Profile.route) {
//            ProfileScreen()
        }


    }
}








@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ParkingDev01Theme {
        Greeting("Android")
    }
}
