package com.example.parkingdev01.ui.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.model.User
import com.example.parkingdev01.data.repository.UserRepository
import com.example.parkingdev01.util.Constants
import com.example.parkingdev01.util.PreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthViewModel(
    private val userRepository: UserRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    suspend fun login(email: String, password: String): Boolean {
        var isSuccess = userRepository.authenticate(email, password)

        if (isSuccess) {
            val user: User? = userRepository.getDetails(email, password)
            user?.let { preferencesManager.saveUser(it) }
            isSuccess = saveToken(Constants.APP_TOKEN)

        }

        return isSuccess
    }

    suspend fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUrl: String
    ): Boolean {
        var isSuccess = userRepository.createUser(
            email,
            password,
            firstName,
            lastName,
            phoneNumber,
            photoUrl
        )

        if (isSuccess) {
            val user: User? = userRepository.getDetails(email, password)
            user.let { preferencesManager.saveUser(it!!) }
            isSuccess = saveToken(Constants.APP_TOKEN)
        }

        return isSuccess
    }


    private suspend fun saveToken(token: String): Boolean{
        val userId = preferencesManager.getUsedId()
        if (userId != -1){
            return userRepository.saveToken(userId, token)
        }
        return false
    }

    suspend fun loginWithGoogle(idToken: String): Boolean {
        try {
            // Authenticate with Firebase using the Google ID token
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = FirebaseAuth.getInstance().signInWithCredential(credential).await()

            // Get the user from Firebase Auth
            val firebaseUser = authResult.user

                // Get user details
                val user = User(
                    id = -1,
                    email = firebaseUser?.email ?: "",
                    firstName = firebaseUser?.displayName ?: "",
                    lastName = firebaseUser?.displayName ?: "",
                    phoneNumber = firebaseUser?.phoneNumber ?: "",
                    photoUrl = firebaseUser?.photoUrl.toString() ?: "",
                    password = "",
                    )

                // Save user details to local storage
                preferencesManager.saveUser(user)

                // Save token if needed
                saveToken(Constants.APP_TOKEN)

                return true // Login successful

        } catch (e: Exception) {
            // Handle login failure
            Log.e(TAG, "Google sign in failed", e)
        }
        return false // Login failed
    }

}
