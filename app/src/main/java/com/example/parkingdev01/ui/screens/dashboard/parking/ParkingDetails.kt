package com.example.parkingdev01.ui.screens.dashboard.parking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun ParkingDetails(
    parkingId: Int,
    parkingViewModel: ParkingViewModel,
    reservationViewModel: ReservationViewModel
) {
    val parking by produceState<Parking?>(null, parkingId) {
        value = parkingViewModel.loadParkingDetails(parkingId)
    }

    var reservationStatus by remember { mutableStateOf<String?>(null) }


    var entryDateTime by remember { mutableStateOf(Calendar.getInstance()) }
    var exitDateTime by remember { mutableStateOf(Calendar.getInstance()) }

    val context = LocalContext.current

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
                ImageRequest.Builder(context).data(data = "$IMAGES_URL${parking!!.photoUrl}")
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
                val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())

                OutlinedTextField(
                    value = dateFormat.format(entryDateTime.time),
                    onValueChange = { },
                    label = { Text("Entry Time") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            showDateTimePicker(context, entryDateTime) { updatedCalendar ->
                                entryDateTime = updatedCalendar
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Select Date"
                            )
                        }
                    }
                )

                OutlinedTextField(
                    value = dateFormat.format(exitDateTime.time),
                    onValueChange = { },
                    label = { Text("Exit Time") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            showDateTimePicker(context, exitDateTime) { updatedCalendar ->
                                exitDateTime = updatedCalendar
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Select Date"
                            )
                        }
                    }
                )

                val price by remember { mutableStateOf("1500") }

                Text(
                    text = "Price: ${price}DA", // Concatenate the price with a label and "DA"
                    fontSize = 24.sp, // Set a bigger font size
                    color = Color.Red, // Set text color to green
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )


                // Book Now button
                Button(
                    onClick = {
                        val reservation = Reservation(
                            id = 1,
                            parkingId = parkingId,
                            userId = 1,
                            placedAt = System.currentTimeMillis(), // Using current time in milliseconds
                            entryTime = entryDateTime.timeInMillis,
                            exitTime = exitDateTime.timeInMillis,
                            price = price.toLong()
                        )

                        CoroutineScope(Dispatchers.Main).launch {
                            val isSuccess = reservationViewModel.bookParkingSpace(reservation)
                            reservationStatus = if (isSuccess) "Reservation successful" else "Reservation failed"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp), // Adding bottom margin of 30.dp
                    enabled = parking!!.availablePlaces > 0 && reservationStatus != "Reservation successful"
                // Disable the button if reservationStatus is not null
                ) {
                    Text("Book Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                // Reservation status text
                reservationStatus?.let { status ->
                    Text(
                        text = status,
                        fontSize = 14.sp,
                        color = if (status == "Reservation successful") Color.DarkGray else Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}




fun showDateTimePicker(
    context: Context,
    calendar: Calendar,
    onDateTimeSelected: (Calendar) -> Unit
) {
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(context, { _, year, month, day ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(context, { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            onDateTimeSelected(calendar)
        }, currentHour, currentMinute, true).show()

    }, currentYear, currentMonth, currentDay).show()
}



