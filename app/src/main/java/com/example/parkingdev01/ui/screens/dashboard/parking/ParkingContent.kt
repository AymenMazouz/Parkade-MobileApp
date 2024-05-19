package com.example.parkingdev01.ui.screens.dashboard.parking

import ParkingCard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ParkingContent(navController: NavHostController, parkingViewModel: ParkingViewModel) {
    var parkingList by remember { mutableStateOf(emptyList<Parking>()) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
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

        // Display the list of parking cards
        for (parking in parkingList) {
            ParkingCard(parking, navController)
        }
    }
}

