package com.example.parkingdev01.ui.screens.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.parkingdev01.R
import com.example.parkingdev01.ui.screens.Destination
import com.example.parkingdev01.ui.viewmodels.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navHostController: NavHostController, authViewModel: AuthViewModel, mGoogleSignInClient: GoogleSignInClient) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showErrorSnackbar by remember { mutableStateOf(false) }



    // Add this function for handling Google Sign-In
    val signInLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { googleAccount ->
                    // Authenticate with Firebase using googleAccount.idToken
                    // Pass necessary parameters to authViewModel.login
                    CoroutineScope(Dispatchers.Main).launch {
                        val success = authViewModel.loginWithGoogle(googleAccount.idToken!!)
                        if (success) {
                            navHostController.navigate(Destination.Dashboard.route) {
                                popUpTo(Destination.Login.route) {
                                    inclusive = true
                                }
                            }
                        } else {
                            showErrorSnackbar = true
                        }
                    }
                }
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    // Function to handle Google Sign-In button click
    fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }


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
                text = "Log In To Continue",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(25.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.DarkGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textStyle = TextStyle(color = Color.Black)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.DarkGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = Color.Black)
            )

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch { // Launch coroutine on the main thread
                        val success = authViewModel.login(email, password)
                        if (!success) {
                            showErrorSnackbar = true
                        } else {
                            navHostController.navigate(Destination.Dashboard.route) {
                                popUpTo(Destination.Login.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                },

                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log In", color = Color.White)
            }

            // Google Sign-In Button
            Button(
                onClick = { signInWithGoogle() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign In with Google", color = Color.White)
            }

            // Snack bar to display error message
            if (showErrorSnackbar) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        Button(onClick = { showErrorSnackbar = false }) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    Text("Wrong Email or password")
                }
            }
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "New on this app? ",
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Sign Up",
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navHostController.navigate("signup")
                    }
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Terms and Conditions | Privacy Policy",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}
