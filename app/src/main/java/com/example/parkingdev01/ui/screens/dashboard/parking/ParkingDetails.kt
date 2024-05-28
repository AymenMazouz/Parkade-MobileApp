package com.example.parkingdev01.ui.screens.dashboard.parking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.data.remote.RetrofitInstance.IMAGES_URL
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import com.example.parkingdev01.ui.viewmodels.ReservationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingDetails(
    parkingId: Int,
    parkingViewModel: ParkingViewModel,
    reservationViewModel: ReservationViewModel
) {
    val parking by produceState<Parking?>(null, parkingId) {
        value = parkingViewModel.loadParkingDetails(parkingId)
    }


    var entryTime by remember { mutableStateOf("06/05/24 08:00:00,000000000") }
    var exitTime by remember { mutableStateOf("07/05/24 18:00:00,000000000") }
    var price by remember { mutableStateOf("1500") }

    if (parking == null) {
        Text("Loading...")
    } else {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = "$IMAGES_URL${parking!!.photoUrl}")
                    .apply {
                        crossfade(true)
                    }.build()
            )

            // Image section
            Image(
                painter = painter,
                contentDescription = "Parking Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            // Information card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = parking!!.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = parking!!.description,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = parking!!.wilaya,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = parking!!.location,
                        fontSize = 16.sp
                    )
                }
            }

            // Form section
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                OutlinedTextField(
                    value = entryTime,
                    onValueChange = { entryTime = it },
                    label = { Text("Entry Time (ms)") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = exitTime,
                    onValueChange = { exitTime = it },
                    label = { Text("Exit Time (ms)") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                // Book Now button
                Button(
                    onClick = {
                        val reservation = Reservation(
                            id = 1,
                            parkingId = parkingId,
                            userId = 1,
                            placedAt = "2024-05-20 10:00:00",
                            entryTime = entryTime,
                            exitTime = exitTime,
                            price = price.toLong()
                        )

                        CoroutineScope(Dispatchers.Main).launch {
                            reservationViewModel.bookParkingSpace(reservation)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp) // Adding bottom margin of 30.dp
                ) {
                    Text("Book Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
