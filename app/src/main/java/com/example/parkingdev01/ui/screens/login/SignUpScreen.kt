package com.example.parkingdev01.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.parkingdev01.R
import com.example.parkingdev01.ui.screens.Destination
import com.example.parkingdev01.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("ayoubbkm1@gmail.com") }
    var password by remember { mutableStateOf("ayoubbkm1") }
    var confirmPassword by remember { mutableStateOf("ayoubbkm1") }
    var firstName by remember { mutableStateOf("ayoubbkm1") }
    var lastName by remember { mutableStateOf("ayoubbkm1") }
    var phoneNumber by remember { mutableStateOf("ayoubbkm1") }
    var photoUrl by remember { mutableStateOf("ayoubbkm1") }


    var errorText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.loginbackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(25.dp))

            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textStyle = TextStyle(color = Color.Black)
            )

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textStyle = TextStyle(color = Color.Black)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textStyle = TextStyle(color = Color.Black)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = Color.Black)
            )

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = Color.Black)
            )

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textStyle = TextStyle(color = Color.Black)
            )

            Text(errorText, color = Color.Red, fontWeight = FontWeight.Bold)

            Button(
                onClick = {
                    errorText = ""

                    if (password != confirmPassword) {
                        errorText = "Password does not match"
                        return@Button
                    } else if (password.isEmpty()) {
                        errorText = "Password cannot be empty"
                        return@Button
                    } else if (firstName.isEmpty()) {
                        errorText = "Full Name cannot be empty"
                        return@Button
                    } else {


                        // Success:
                        CoroutineScope(Dispatchers.Main).launch { // Launch coroutine on the main thread
                            val success = authViewModel.signup(email, password, firstName, lastName, phoneNumber, photoUrl )
                            if (!success) {
                                errorText = "Error Happened During Registration!"
                            } else {
                                navController.navigate(Destination.Dashboard.route) {
                                    popUpTo(Destination.SignUp.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                    // TODO: Email Validity
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up", color = Color.White)
            }
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already Have an Account?",
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Log In",
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    }
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Terms and Conditions | Privacy Policy",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}
