package com.example.parkingdev01.ui.screens.dashboard.parking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parkingdev01.R
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ParkingContent(parkingViewModel: ParkingViewModel) {
    var parkingList : List<Parking>

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch { // Launch coroutine on the main thread
                    parkingList = parkingViewModel.loadParkingRemote()
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = "Refresh")
        }
        Text(
            text = "Parking Screen",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}