package com.example.parkingdev01

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
import com.example.parkingdev01.data.AppDatabase
import com.example.parkingdev01.data.repository.ParkingRepository
import com.example.parkingdev01.data.repository.ReservationRepository
import com.example.parkingdev01.data.repository.UserRepository
import com.example.parkingdev01.ui.screens.Destination
import com.example.parkingdev01.ui.screens.dashboard.DashboardScreen
import com.example.parkingdev01.ui.screens.login.LoginScreen
import com.example.parkingdev01.ui.screens.login.SignUpScreen
import com.example.parkingdev01.ui.theme.ParkingDev01Theme
import com.example.parkingdev01.ui.viewmodels.AuthViewModel
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import com.example.parkingdev01.ui.viewmodels.ReservationViewModel
import com.example.parkingdev01.util.PreferencesManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    private val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    private lateinit var preferencesManager: PreferencesManager
    private val userRepository by lazy { UserRepository() }
    private val parkingRepository by lazy { ParkingRepository(database.parkingDao(),this) }

    private val reservationRepository by lazy { ReservationRepository(database.reservationDao(),this) }

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth




    private val parkingViewModel: ParkingViewModel by lazy { ParkingViewModel(parkingRepository) }

    private val reservationViewModel: ReservationViewModel by lazy {
        ReservationViewModel(reservationRepository)
    }

    private val authViewModel: AuthViewModel by lazy {
        AuthViewModel(userRepository, preferencesManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



        preferencesManager = PreferencesManager(this) // Initialize preferencesManager here
        setContent {
            ParkingDev01Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val userExists = preferencesManager.getUser() != null

                    if (userExists) {
                        DashboardScreen(
                            parkingViewModel,
                            reservationViewModel = reservationViewModel,
                            preferencesManager,
                            this
                        )
                    } else {
                        ParkingAppNavigation(
                            navController = navController,
                            authViewModel,
                            parkingViewModel,
                            reservationViewModel,
                            preferencesManager,
                            this,
                            mGoogleSignInClient

                        )
                    }
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
    reservationViewModel: ReservationViewModel,
    preferencesManager: PreferencesManager,
    activity: ComponentActivity,
    mGoogleSignInClient: GoogleSignInClient
) {
    NavHost(navController, startDestination = Destination.Login.route) {

        // Login & Sign Up Destinations
        composable(Destination.Login.route) {
            LoginScreen(navController, authViewModel, mGoogleSignInClient = mGoogleSignInClient )
        }

        composable(Destination.SignUp.route) {
            SignUpScreen(navController, authViewModel)
        }

        // Dashboard Destination
        composable(Destination.Dashboard.route) {
            DashboardScreen(
                parkingViewModel,
                reservationViewModel = reservationViewModel,
                preferencesManager = preferencesManager,
                activity = activity
            )
        }
    }
}
