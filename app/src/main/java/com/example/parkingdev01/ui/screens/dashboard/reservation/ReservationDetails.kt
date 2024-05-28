package com.example.parkingdev01.ui.screens.dashboard.reservation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.ui.viewmodels.ReservationViewModel
import com.lightspark.composeqr.DotShape
import com.lightspark.composeqr.QrCodeColors
import com.lightspark.composeqr.QrCodeView

@Composable
fun ReservationDetails(
    reservationId: Int,
    reservationViewModel: ReservationViewModel
) {
    val reservation by produceState<Reservation?>(null, reservationId) {
        value = reservationViewModel.loadReservationDetails(reservationId)
    }

    if (reservation == null) {
        Text("Loading...")
    } else {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Reservation ID: ${reservation!!.id}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier.size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                QrCodeView(
                    data = reservation!!.id.toString(),
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.White),
                    colors = QrCodeColors(
                        background = Color.White,
                        foreground = Color.Black
                    ),
                    dotShape = DotShape.Square
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(5.dp)
                    ) {
                        // You can add any additional content here if needed
                    }
                }
            }
        }
    }
}
