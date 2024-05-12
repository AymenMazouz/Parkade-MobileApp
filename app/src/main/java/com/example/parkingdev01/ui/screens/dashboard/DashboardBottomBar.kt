

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


