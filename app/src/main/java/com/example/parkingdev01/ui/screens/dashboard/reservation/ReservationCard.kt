package com.example.parkingdev01.ui.screens.dashboard.reservation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.ui.screens.Destination
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Utility function to convert timestamp to formatted date string
fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun ReservationCard(reservation: Reservation, navController: NavHostController) {
    OutlinedCard(
        modifier = Modifier
            .width(380.dp)
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp) // Adjust padding to your preference
            .clickable {
                navController.navigate(Destination.ReservationDetails.createRoute(reservation.id))
            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Start,
                    contentDescription = "Entry",
                    tint = Color(0xFF5F93FB), // Light blue color
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Entry Time: ${formatDate(reservation.entryTime)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp, start = 7.dp)
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.PriceChange,
                    contentDescription = "Price",
                    tint = Color(0xFF5F93FB), // Light blue color
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Total Price: ${reservation.price} DA",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(bottom = 4.dp, start = 3.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = "Reservation ID",
                    tint = Color(0xFF5F93FB), // Light blue color
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Reservation ID: ${reservation.id}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp, start = 10.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Entry",
                    tint = Color(0xFF5F93FB), // Light blue color
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Exit Time: ${formatDate(reservation.exitTime)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp, start = 7.dp)
                )
            }
        }
    }
}
