

package com.example.parkingdev01.ui.screens.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.parkingdev01.ui.screens.Destination


@Composable
fun DashboardBottomBar(navHostController: NavHostController) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        NavigationBarItem(
            selected = selectedItemIndex == 0,
            onClick = {
                selectedItemIndex = 0
                navHostController.navigate(Destination.Map.route)
            },
            icon = {
                if (selectedItemIndex == 0) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = "Map",
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary // Tint color for selected item
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = "Map",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        )
        NavigationBarItem(
            selected = selectedItemIndex == 1,
            onClick = {
                selectedItemIndex = 1
                navHostController.navigate(Destination.Parkings.route)
            },
            icon = {
                if (selectedItemIndex == 1) {
                    Icon(
                        imageVector = Icons.Default.LocalParking,
                        contentDescription = "Parking",
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary // Tint color for selected item
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.LocalParking,
                        contentDescription = "Parking",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        )
        NavigationBarItem(
            selected = selectedItemIndex == 2,
            onClick = {
                selectedItemIndex = 2
                navHostController.navigate(Destination.Reservations.route) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(Destination.Reservations.route) {
                        inclusive = true
                    }
                }
            },
            icon = {
                if (selectedItemIndex == 2) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Reservations",
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary // Tint color for selected item
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Reservations",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        )

        NavigationBarItem(
            selected = selectedItemIndex == 3,
            onClick = {
                selectedItemIndex = 3
                navHostController.navigate(Destination.Profile.route)
            },
            icon = {
                if (selectedItemIndex == 3) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary // Tint color for selected item
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        )
    }
}


