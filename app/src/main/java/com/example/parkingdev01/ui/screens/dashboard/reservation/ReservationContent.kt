package com.example.parkingdev01.ui.screens.dashboard.reservation

import ParkingCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.parkingdev01.R
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import com.example.parkingdev01.ui.viewmodels.ReservationViewModel
import com.example.parkingdev01.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationContent(navController: NavHostController, reservationViewModel: ReservationViewModel) {
    var reservationList by remember { mutableStateOf(emptyList<Reservation>()) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar with notification and back icons
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                // Handle back navigation
                                navController.popBackStack()
                            }
                    )
                    Spacer(modifier = Modifier.width(320.dp)) // Add spacing between icons
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notification",
                        modifier = Modifier
                            .size(28.dp)
                    )

                }
            },
            // backgroundColor = Color.Transparent, // Transparent background for app bar
            // elevation = 0.dp // No elevation for app bar
        )

        // First section as a card with white background
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .height(170.dp), // Set the height to 200.dp
            shape = RoundedCornerShape(16.dp), // Rounded corners for the card
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF5F93FB), // Light baby blue
                                Color(0xFF78BDF3)  // Slightly darker baby blue
                            ),
                            startX = 0f,
                            endX = 1000f
                        )
                    ),
                contentAlignment = Alignment.CenterStart // Align content to the left
            ) {
                Row {
                    Column(
                        modifier = Modifier.padding(start = 16.dp), // Add padding to the left
                        horizontalAlignment = Alignment.Start // Align text to the left
                    ) {
                        Text(
                            text = "Welcome Mazouz,",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp, // Définir la taille de la police
                            modifier = Modifier.padding(bottom = 11.dp)
                        )
                        Text(
                            text = "Check in using the QR code",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp, // Définir la taille de la police
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        Text(
                            text = "Here you find the list of your Reservations",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(0.3f) // Take 30% of the width
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.qr), // Replace with your image resource
                            contentDescription = "reservationImage",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                        )
                    }
                }
            }
        }

        // Refresh icon
        IconButton(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    reservationList = reservationViewModel.loadReservationByUser(Constants.USER.id)
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh", modifier = Modifier.size(28.dp))
        }

        // Second section with scrollable parkings
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Occupy remaining space
        ) {
            item {
                // Display the list of parking cards
                for (reservation in reservationList) {
                    ReservationCard(reservation, navController)
                }
            }
        }
    }
}
