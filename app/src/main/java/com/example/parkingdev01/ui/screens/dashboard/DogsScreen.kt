package com.example.parkingdev01.ui.screens.dashboard

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.example.parkingdev01.data.model.Dog
import com.example.parkingdev01.data.remote.RetrofitInstance
import com.example.parkingdev01.ui.viewmodels.DogViewModel
import org.jetbrains.annotations.Async


@Composable
fun DogsScreen(dogViewModel: DogViewModel) {
    val context = LocalContext.current


    val dogs = dogViewModel.dogs.value


    LazyColumn {
        itemsIndexed(dogs) { _, it ->
            Row (

                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .padding(4.dp)
                    .background(Color(0xFFE0E0E0))
                    .clickable {
                        Toast.makeText(context,it.name, Toast.LENGTH_SHORT).show()
                    }


            ) {
                AsyncImage(model = RetrofitInstance.BASE_URL +  it.img ,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(8.dp)
                )

                Column(
                    modifier = Modifier.weight(2f)
                ) {
                    Text(text = it.name , fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it.description,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }

}