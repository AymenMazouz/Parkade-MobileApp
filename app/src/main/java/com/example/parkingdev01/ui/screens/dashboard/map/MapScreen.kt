package com.example.parkingdev01.ui.screens.dashboard.map

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.plcoding.mapscomposeguide.presentation.MapsViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MapScreen(
    viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

    // Define marker coordinates
    val markerCoordinates = listOf(
        LatLng(36.7538, 3.0588),  // Example marker 1
        LatLng(36.7519, 3.0542),  // Example marker 2
        LatLng(36.7642, 3.1822),  // Example marker 3
        LatLng(36.7639, 3.0554),  // Example marker 4
        LatLng(36.7499, 3.0869)   // Example marker 5
    )

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
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = viewModel.state.properties,
            uiSettings = uiSettings,
            onMapLongClick = {

            }
        ) {
            // Add markers
            markerCoordinates.forEach { coordinate ->
                Marker(
                    position = coordinate,
                   // onClick = {
                        // Handle marker click here, you can display a card with its name
                        // For example, you can use a Snackbar or a Dialog to show the name
                        // of the clicked marker.
                        // For simplicity, I'll just print the marker's latitude and longitude.
                     //   println("Marker Clicked")
                   // }
                )
            }
        }
    }
}
