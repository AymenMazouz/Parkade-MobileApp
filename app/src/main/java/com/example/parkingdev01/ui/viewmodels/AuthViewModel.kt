package com.example.parkingdev01.ui.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.model.User
import com.example.parkingdev01.data.repository.UserRepository
import com.example.parkingdev01.util.Constants
import com.example.parkingdev01.util.PreferencesManager
import com.example.parkingdev01.util.generateRandomPassword
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
            val user: User? = userRepository.getDetails(email)
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
            val user: User? = userRepository.getDetails(email)
            user.let { preferencesManager.saveUser(it!!) }
            isSuccess = saveToken(Constants.APP_TOKEN)
        }

        return isSuccess
    }


    private suspend fun saveToken(token: String): Boolean {
        val userId = preferencesManager.getUsedId()
        if (userId != -1) {
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

            val displayName = firebaseUser?.displayName
            val names = displayName?.split(" ")

            val firstName = names?.first().toString()
            val lastName = names?.drop(1)?.joinToString(" ").toString()

            val randomPassword = generateRandomPassword(12)

            // Get user details
            val user = User(
                id = -1,
                email = firebaseUser!!.email.toString(),
                firstName = firstName,
                lastName = lastName,
                phoneNumber = firebaseUser.phoneNumber ?: "1234",
                photoUrl = firebaseUser.photoUrl.toString(),
                password = randomPassword,
            )

            val success = signup(
                email = user.email,
                password = user.password,
                firstName = user.firstName,
                lastName = user.lastName,
                phoneNumber = user.phoneNumber,
                photoUrl = user.photoUrl
            )
            return success

        // Login successful

        } catch (e: Exception) {
            // Handle login failure
            Log.e(TAG, "Google sign in failed", e)
        }
        return false // Login failed
    }

}
