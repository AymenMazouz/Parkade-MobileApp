
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.parkingdev01.util.PreferencesManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingContent(
    navController: NavHostController,
    preferencesManager: PreferencesManager,
    parkingViewModel: ParkingViewModel
) {
    var parkingList by remember { mutableStateOf(emptyList<Parking>()) }
    val user = preferencesManager.getUser()

    LaunchedEffect(Unit) {
        // Load parkings automatically when the composable is launched
        parkingList = parkingViewModel.loadParkingRemote()
    }

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
                }
            }
        )

        // First section as a card with white background
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .height(150.dp), // Set the height to 200.dp
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
                        if (user != null) {
                            Text(
                                text = "Welcome ${user.firstName}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp, // Définir la taille de la police
                                modifier = Modifier.padding(bottom = 11.dp)
                            )
                        }
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

      //here add a text Parkings List and an vertical line in baby bleu
        Text(
            text = "Parkings List :",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 23.dp, top = 10.dp)
        )
        Divider(
            color = Color(0xFF5F93FB), // Baby blue color
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 23.dp, vertical = 5.dp)
        )

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
