package com.example.parkingdev01.ui.screens.dashboard.parking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel


@Composable
fun ParkingDetails(parkingId: Int, parkingViewModel: ParkingViewModel) {
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
            // Display other parking details
        }
    }
}