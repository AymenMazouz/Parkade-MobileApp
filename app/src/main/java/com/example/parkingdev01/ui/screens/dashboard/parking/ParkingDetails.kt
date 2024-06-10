package com.example.parkingdev01.ui.screens.dashboard.parking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PriceChange
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.model.Reservation
import com.example.parkingdev01.data.remote.RetrofitInstance.IMAGES_URL
import com.example.parkingdev01.data.repository.NotificationRepository
import com.example.parkingdev01.ui.viewmodels.NotificationViewModel
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import com.example.parkingdev01.ui.viewmodels.ReservationViewModel
import com.example.parkingdev01.util.Constants
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
    val notificationRepository by lazy { NotificationRepository() }
    val notificationViewModel: NotificationViewModel by lazy { NotificationViewModel(notificationRepository) }




    var reservationStatus by remember { mutableStateOf<String>("Init") }

    // Initialize exitDateTime
    var entryDateTime by remember {
        mutableStateOf(Calendar.getInstance().apply {
            add(Calendar.HOUR, 1)
        })
    }
    // Initialize exitDateTime
    var exitDateTime by remember {
        mutableStateOf(Calendar.getInstance().apply {
            add(Calendar.HOUR, 25)
        })
    }
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
            Text(
                text = "Parkings Details :",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 5.dp, top = 10.dp)
            )
            TabRowDefaults.Divider(
                color = Color(0xFF5F93FB), // Baby blue color
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp)
            )
            // Image section
            Image(
                painter = painter,
                contentDescription = "Parking Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 7.dp)
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
                Column(modifier = Modifier.padding(7.dp)) {
                    Text(
                        textAlign = TextAlign.Left,
                        text = parking!!.name,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = parking!!.description,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "Map",
                            tint = Color(0xFF5F93FB), // Light blue color
                            modifier = Modifier
                                .size(20.dp)
                        )

                        Text(
                            text = "${parking!!.commune}, ${parking!!.location}, ${parking!!.wilaya}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 5.dp)
                        )

                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Default.PriceChange,
                            contentDescription = "Price",
                            tint = Color(0xFF5F93FB), // Light blue color
                            modifier = Modifier
                                .size(20.dp)
                        )

                        Text(
                            text = "Daily Price : ${parking!!.dailyPrice} DA",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 5.dp)
                        )

                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = "Price",
                            tint = Color(0xFF5F93FB), // Light blue color
                            modifier = Modifier
                                .size(20.dp)
                        )

                        Text(
                            text = "Size : ${parking!!.totalPlaces} Place",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 5.dp)
                        )

                    }
                }
            }
            Text(
                text = "Booking Form :",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 5.dp, top = 10.dp)
            )
            TabRowDefaults.Divider(
                color = Color(0xFF5F93FB), // Baby blue color
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp)
            )
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

                val hourlyRate = parking?.dailyPrice?.div(24) ?: 0.0

                val durationInMillis = exitDateTime.timeInMillis - entryDateTime.timeInMillis
                val hours = durationInMillis / (1000 * 60 * 60)
                val price = (hours * hourlyRate).toLong()

                Text(
                    text = "The Price will be : $price DA",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )


                // Book Now button
                Button(
                    onClick = {
                        val reservation = Reservation(
                            parkingId = parkingId,
                            userId = Constants.USER.id,
                            placedAt = System.currentTimeMillis(), // Using current time in milliseconds
                            entryTime = entryDateTime.timeInMillis,
                            exitTime = exitDateTime.timeInMillis,
                            price = price
                        )

                        CoroutineScope(Dispatchers.Main).launch {
                            var isSuccess = reservationViewModel.bookParkingSpace(reservation)
                            reservationStatus = if (isSuccess) "Reservation successful" else "Reservation failed"

                            if (isSuccess){

                                // Schedule notification
                                isSuccess = notificationViewModel.scheduleNotification(
                                    token = Constants.APP_TOKEN, // Replace with actual user token
                                    title = "Reservation Reminder",
                                    body = "Your parking reservation at ${parking!!.name} is starting soon.",
                                    time = (reservation.entryTime / 1000) - 3590  // One hour before
                                )
                                reservationStatus = if (isSuccess) {
                                    "Reservation successful. Notification Scheduled."
                                } else {
                                    "Reservation successful. Notification schedule failed."
                                }
                            } else {
                                reservationStatus = "Reservation booking failed."
                            }

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp), // Adding bottom margin of 30.dp
                    enabled = parking!!.availablePlaces > 0 && !(reservationStatus.contains("Reservation successful"))  && price > 0
                // Disable the button if reservationStatus is not null
                ) {
                    Text("Book Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                // Reservation status text
                reservationStatus.let { status ->
                    Text(
                        text = status,
                        fontSize = 14.sp,
                        color = if (status == "Reservation successful") Color.DarkGray else Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

            }
        }
    }
}




fun showDateTimePicker(
    context: Context,
    initialCalendar: Calendar,
    onDateTimeSelected: (Calendar) -> Unit
) {
    val currentYear = initialCalendar.get(Calendar.YEAR)
    val currentMonth = initialCalendar.get(Calendar.MONTH)
    val currentDay = initialCalendar.get(Calendar.DAY_OF_MONTH)
    val currentHour = initialCalendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = initialCalendar.get(Calendar.MINUTE)

    DatePickerDialog(context, { _, year, month, day ->
        TimePickerDialog(context, { _, hour, minute ->
            val updatedCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }

            val currentTime = Calendar.getInstance().timeInMillis
            if (updatedCalendar.timeInMillis >= currentTime) {
                onDateTimeSelected(updatedCalendar)
            } else {
                // Show a message to the user that the selected date-time is invalid
                Toast.makeText(context, "Selected date-time cannot be in the past.", Toast.LENGTH_SHORT).show()
            }
        }, currentHour, currentMinute, true).show()
    }, currentYear, currentMonth, currentDay).show()
}



