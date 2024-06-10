
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.remote.RetrofitInstance.IMAGES_URL
import com.example.parkingdev01.ui.screens.Destination
import com.example.parkingdev01.ui.screens.dashboard.map.MapEvent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.plcoding.mapscomposeguide.presentation.MapsViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    parkingList: List<Parking>
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

    var selectedParking by remember { mutableStateOf<Parking?>(null) }

    // Remember the camera position state
    val cameraPositionState = rememberCameraPositionState()

    // LaunchedEffect to move the camera to the desired position when the map is first loaded
    LaunchedEffect(Unit) {
        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(LatLng(36.7372, 3.0867), 12f))
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(MapEvent.ToggleFalloutMap)
                },
            ) {
                Icon(
                    imageVector = if (viewModel.state.isFalloutMap) {
                        Icons.Default.ToggleOff
                    } else Icons.Default.ToggleOn,
                    contentDescription = "Toggle Fallout map",
                )
            }
        },
    ) {
        Box {
            GoogleMap(
                properties = viewModel.state.properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState,
                onMapLongClick = {

                }
            ) {
                // Add markers using parkingList data
                parkingList.forEach { parking ->
                    Marker(
                        position = LatLng(parking.latitude, parking.longitude),
                        onClick = {
                            selectedParking = parking
                            true
                        }
                    )
                }
            }

            // Display card if a parking is selected
            selectedParking?.let { parking ->
                Card(
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.BottomCenter),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                navController.navigate(Destination.ParkingDetails.createRoute(parking.id))
                            }
                    ) {
                        Text(
                            text = parking.name,
                            fontWeight = FontWeight.Bold, // Make parking name bold
                            fontSize = 20.sp, // Increase font size
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = "map",
                                tint = Color(0xFF5F93FB),
                                modifier = Modifier
                                    .size(20.dp)
                            )
                            Text(
                                text = "${parking.commune}, ${parking.wilaya}",
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,

                                modifier = Modifier.padding(bottom = 1.dp, start = 5.dp)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.DirectionsCar,
                                contentDescription = "car",
                                tint = Color(0xFF5F93FB), // Light blue color
                                modifier = Modifier
                                    .size(20.dp)
                            )

                            Text(
                                text = "Total Places: ${parking.totalPlaces}",
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,

                                modifier = Modifier.padding(bottom = 3.dp, start = 5.dp)
                            )
                        }

                        // Assuming parking.imageUri is a URI or URL to the parking image
                        Image(
                            painter = rememberAsyncImagePainter("$IMAGES_URL${parking.photoUrl}"),
                            contentDescription = "Parking Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 8.dp) // Add padding to the bottom of the image
                        )
                        Button(
                            onClick = { selectedParking = null },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Dismiss")
                        }
                    }
                }
            }
        }
    }
}
