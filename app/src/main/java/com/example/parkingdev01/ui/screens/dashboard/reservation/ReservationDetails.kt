package com.example.parkingdev01.ui.screens.dashboard.reservation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    reservationViewModel: ReservationViewModel,
) {
    val reservation by produceState<Reservation?>(null, reservationId) {
        value = reservationViewModel.loadReservationDetails(reservationId)
    }

    if (reservation == null) {
        Text("Loading...")
    } else {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Reservation Details :${reservation!!.id}",
                color = Color.Black,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 5.dp, top = 10.dp)
            )
            TabRowDefaults.Divider(
                color = Color(0xFF5F93FB), // Baby blue color
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Map",
                    tint = Color(0xFF5F93FB), // Light blue color
                    modifier = Modifier
                        .size(20.dp)
                )

                Text(
                    text = "${reservation!!.entryTime}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 5.dp)
                )

            }
















            Text(
                text = "QR Code :",
                color = Color.Black,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 5.dp, top = 10.dp)
            )
            TabRowDefaults.Divider(
                color = Color(0xFF5F93FB), // Baby blue color
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp)
            )
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.White)
                    .align(Alignment.CenterHorizontally), // Center the QR code
                contentAlignment = Alignment.Center
            ) {
                QrCodeView(
                    data = reservation!!.id.toString(),
                    modifier = Modifier.size(200.dp),
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
                            .padding(10.dp)
                    ) {
                    }
                }
            }
            Text(
                text = "Scan This Code at the entery of the Parking",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 70.dp,start=10.dp,top=10.dp) ,
                textAlign = TextAlign.Center,

                )

        }
    }
}
