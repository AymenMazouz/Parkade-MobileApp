package com.example.parkingdev01.ui.screens.dashboard.parking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import com.example.parkingdev01.ui.viewmodels.ReservationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ParkingDetails(
    parkingId: Int,
    parkingViewModel: ParkingViewModel,
    reservationViewModel: ReservationViewModel
) {
    val parking by produceState<Parking?>(null, parkingId) {
        value = parkingViewModel.loadParkingDetails(parkingId)
    }

    if (parking == null) {
        Text("Loading...")
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(parking!!.name, style = MaterialTheme.typography.h6)
            Text(parking!!.description, style = MaterialTheme.typography.h6)
            Text(parking!!.wilaya, style = MaterialTheme.typography.h6)
            Text(parking!!.location, style = MaterialTheme.typography.h6)

            Button(onClick = {

                val reservation = Reservation(
                    id = 1,
                    parkingId = 123,
                    userId = 456,
                    placedAt = "2024-05-20 10:00:00", // Sample timestamp for when the reservation was placed
                    entryTime = "1621454400000", // Sample entry time in milliseconds since the epoch
                    exitTime = "1621465200000", // Sample exit time in milliseconds since the epoch
                    price = 1500L // Sample price for the reservation
                )

                CoroutineScope(Dispatchers.Main).launch { // Launch coroutine on the main thread

                    reservationViewModel.bookParkingSpace(reservation)

                }
            }) {
                Text("Book Now")
            }
            // Display other parking details
        }
    }
}