package com.example.parkingdev01.ui.screens.dashboard.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.parkingdev01.ui.screens.Destination


@Composable
fun ProfileContent(navHostController: NavHostController){


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile Page",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 30.dp)

        )
        Divider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            color = Color.Gray // Change the color as needed
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 40.dp)

        ){
            Text(
                text = "Full Name:",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray,

                )
            Text(
                text = "From Pref File",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(10.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 40.dp)

        ){
            Text(
                text = "Email:",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray,

                )
            Text(
                text = "From Pref File",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                navHostController.navigate(Destination.Login.route) {
                    popUpTo(Destination.Login.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 40.dp)
        ) {
            Text("Log Out", color = Color.White)
        }

    }
}