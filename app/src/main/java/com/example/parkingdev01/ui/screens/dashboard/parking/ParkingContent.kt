import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.parkingdev01.R
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.ui.viewmodels.ParkingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingContent(navController: NavHostController, parkingViewModel: ParkingViewModel) {
    var parkingList by remember { mutableStateOf(emptyList<Parking>()) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar with notification and back icons
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                // Handle back navigation
                                navController.popBackStack()
                            }
                    )
                    Spacer(modifier = Modifier.width(320.dp)) // Add spacing between icons
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notification",
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
            },
            // backgroundColor = Color.Transparent, // Transparent background for app bar
            // elevation = 0.dp // No elevation for app bar
        )

        // First section as a card with white background
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .height(170.dp), // Set the height to 200.dp
            shape = RoundedCornerShape(16.dp), // Rounded corners for the card
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF5F93FB), // Light baby blue
                                Color(0xFF78BDF3)  // Slightly darker baby blue
                            ),
                            startX = 0f,
                            endX = 1000f
                        )
                    ),
                contentAlignment = Alignment.CenterStart // Align content to the left
            ) {
                Row {
                    Column(
                        modifier = Modifier.padding(start = 16.dp), // Add padding to the left
                        horizontalAlignment = Alignment.Start // Align text to the left
                    ) {
                        Text(
                            text = "Welcome Mazouz,",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp, // Définir la taille de la police
                            modifier = Modifier.padding(bottom = 11.dp)
                        )
                        Text(
                            text = "Find a place to your car",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp, // Définir la taille de la police
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        Text(
                            text = "Here you find the list of the parkings",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(0.27f) // Take 30% of the width
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.parkings), // Replace with your image resource
                            contentDescription = "Parking Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        // Refresh icon
        IconButton(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    parkingList = parkingViewModel.loadParkingRemote()
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh", modifier = Modifier.size(28.dp))
        }

        // Second section with scrollable parkings
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Occupy remaining space
        ) {
            item {
                // Display the list of parking cards
                for (parking in parkingList) {
                    ParkingCard(parking, navController)
                }
            }
        }
    }
}
