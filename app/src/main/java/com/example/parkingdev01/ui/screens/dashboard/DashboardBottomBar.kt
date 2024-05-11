

package com.example.parkingdev01.ui.screens.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parkingdev01.ui.screens.Destination


@Composable
fun DashboardBottomBar(
    navController: NavController,
    onItemSelected: (Int) -> Unit,
    selectedItemIndex: Int
) {
    val items = listOf(
        Destination.Map,
        Destination.Parkings,
        Destination.Reservations,
        Destination.Profile
    )

    NavigationBar {
        items.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    onItemSelected(index)
                    navController.navigate(destination.route)
                },
                icon = {
                    val icon = when (destination) {
                        Destination.Map -> Icons.Default.Map
                        Destination.Parkings -> Icons.Default.LocalParking
                        Destination.Reservations -> Icons.AutoMirrored.Filled.List
                        Destination.Profile -> Icons.Default.Person
                        else -> null
                    }

                    icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = destination.route,
                            modifier = Modifier.padding(8.dp),
                            tint = if (selectedItemIndex == index) MaterialTheme.colorScheme.primary else LocalContentColor.current
                        )
                    }
                }
            )
        }
    }
}
//
//@Composable
//fun DashboardBottomBar(navController: NavController) {
//    var selectedItemIndex by rememberSaveable {
//        mutableIntStateOf(0)
//    }
//
//    NavigationBar {
//        NavigationBarItem(
//            selected = selectedItemIndex == 0,
//            onClick = {
//                selectedItemIndex = 0
//                navController.navigate(Destination.Map.route)
//            },
//            icon = {
//                if (selectedItemIndex == 0) {
//                    Icon(
//                        imageVector = Icons.Default.Map,
//                        contentDescription = "Map",
//                        modifier = Modifier.padding(8.dp),
//                        tint = MaterialTheme.colorScheme.primary // Tint color for selected item
//                    )
//                } else {
//                    Icon(
//                        imageVector = Icons.Default.Map,
//                        contentDescription = "Map",
//                        modifier = Modifier.padding(8.dp)
//                    )
//                }
//            }
//        )
//        NavigationBarItem(
//            selected = selectedItemIndex == 1,
//            onClick = {
//                selectedItemIndex = 1
//                navController.navigate(Destination.Parkings.route)
//            },
//            icon = {
//                if (selectedItemIndex == 1) {
//                    Icon(
//                        imageVector = Icons.Default.LocalParking,
//                        contentDescription = "Parking",
//                        modifier = Modifier.padding(8.dp),
//                        tint = MaterialTheme.colorScheme.primary // Tint color for selected item
//                    )
//                } else {
//                    Icon(
//                        imageVector = Icons.Default.LocalParking,
//                        contentDescription = "Parking",
//                        modifier = Modifier.padding(8.dp)
//                    )
//                }
//            }
//        )
//        NavigationBarItem(
//            selected = selectedItemIndex == 2,
//            onClick = {
//                selectedItemIndex = 2
//                navController.navigate(Destination.Reservations.route) {
//                    launchSingleTop = true
//                    restoreState = true
//                    popUpTo(Destination.Reservations.route) {
//                        inclusive = true
//                    }
//                }
//            },
//            icon = {
//                if (selectedItemIndex == 2) {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.List,
//                        contentDescription = "Reservations",
//                        modifier = Modifier.padding(8.dp),
//                        tint = MaterialTheme.colorScheme.primary // Tint color for selected item
//                    )
//                } else {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.List,
//                        contentDescription = "Reservations",
//                        modifier = Modifier.padding(8.dp)
//                    )
//                }
//            }
//        )
//
//        NavigationBarItem(
//            selected = selectedItemIndex == 3,
//            onClick = {
//                selectedItemIndex = 3
//                navController.navigate(Destination.Profile.route)
//            },
//            icon = {
//                if (selectedItemIndex == 3) {
//                    Icon(
//                        imageVector = Icons.Default.Person,
//                        contentDescription = "Profile",
//                        modifier = Modifier.padding(8.dp),
//                        tint = MaterialTheme.colorScheme.primary // Tint color for selected item
//                    )
//                } else {
//                    Icon(
//                        imageVector = Icons.Default.Person,
//                        contentDescription = "Profile",
//                        modifier = Modifier.padding(8.dp)
//                    )
//                }
//            }
//        )
//    }
//}
//

