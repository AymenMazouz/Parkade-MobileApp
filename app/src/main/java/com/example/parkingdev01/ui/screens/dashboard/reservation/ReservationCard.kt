package com.example.parkingdev01.ui.screens.dashboard.reservation
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.data.remote.RetrofitInstance.IMAGES_URL
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
            Text(
                text = "Reservation ID: ${reservation.id}",
               // color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}
