package com.example.parkingdev01.ui.screens.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@Composable
fun DashboardScreen() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            DashboardBottomBar(navController)
        }
    ) { innerPadding ->
        DashboardNavGraph(navController = navController, Modifier.padding(innerPadding).padding(10.dp))
    }
}
