
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.remote.RetrofitInstance.IMAGES_URL
import com.example.parkingdev01.ui.screens.Destination
import com.example.parkingdev01.ui.screens.dashboard.map.MapEvent
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.plcoding.mapscomposeguide.presentation.MapsViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                        .padding(16.dp)
                        .align(Alignment.BottomCenter),
                    elevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                navController.navigate(Destination.ParkingDetails.createRoute(parking.id))
                            }
                    ) {
                        Text(text = "Parking Name: ${parking.name}")
                        // Assuming parking.imageUri is a URI or URL to the parking image
                        Image(
                            painter = rememberAsyncImagePainter("$IMAGES_URL${parking.photoUrl}"),
                            contentDescription = "Parking Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
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
