package com.example.parkingdev01.ui.screens.dashboard.reservation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.ui.screens.Destination

@Composable
fun ReservationCard(reservation: Reservation, navController: NavHostController) {
    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate(Destination.ReservationDetails.createRoute(reservation.id))},
        shape = RoundedCornerShape(8.dp),
      //  border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.PriceChange,
                    contentDescription = "Entry",
                    tint = Color(0xFF5F93FB), // Light blue color
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Entry Time : ${reservation.entryTime}",
                    // color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp,start=7.dp)
                )

            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = "Price",
                    tint = Color(0xFF5F93FB), // Light blue color
                    modifier = Modifier
                        .size(20.dp)
                )

                Text(
                    text = "Reservation ID: ${reservation.id}",
                    // color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp,start=7.dp)
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
                    text = "Price : ${reservation.price}",
                    // color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp,start=7.dp)
                )

            }

        }
    }
}
