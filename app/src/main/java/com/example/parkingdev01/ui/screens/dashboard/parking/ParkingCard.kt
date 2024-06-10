
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.remote.RetrofitInstance.IMAGES_URL
import com.example.parkingdev01.ui.screens.Destination

@Composable
fun ParkingCard(parking: Parking, navController: NavHostController) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = "$IMAGES_URL${parking.photoUrl}")
            .apply {
                crossfade(true)
            }
            .build()
    )


    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(0.dp, Color.Transparent), // Set border to transparent
        modifier = Modifier
            .size(width = 420.dp, height = 110.dp)
            .padding(vertical = 2.dp, horizontal = 16.dp) // Add margins
            .clickable {
                navController.navigate(Destination.ParkingDetails.createRoute(parking.id))
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize()
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 5.dp)
                        .clip(RoundedCornerShape(8.dp)) // Set border radius
                        .border(BorderStroke(0.dp, Color.Transparent)) // Remove border
                        .padding(4.dp) // Add padding to the image
                )
                Column(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(
                        text = parking.name,
                        fontWeight = FontWeight.Bold, // Make parking name bold
                        fontSize = 20.sp, // Increase font size
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Text(
                        text = "${parking.commune}, ${parking.wilaya}",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                    Text(
                        text = "Total Places: ${parking.totalPlaces}",
                        color = Color(0xFF5F93FB), // Light blue color
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                }
            }
            if (parking.availablePlaces == 0) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "No available places",
                    tint = Color(0xFFFF8A80), // Lighter shade of red matching the blue
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterEnd)
                        .padding(end = 20.dp) // Adjust this value to position it
                )
            } else { // There are available places
                Icon(
                    imageVector = Icons.Filled.LockOpen,
                    contentDescription = "Available places",
                    tint = Color(0xFF4CAF50), // Baby green color
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterEnd)
                        .padding(end = 20.dp) // Adjust this value to position it
                )
            }
        }
    }
}
