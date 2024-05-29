package com.example.parkingdev01.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.model.User
import com.example.parkingdev01.data.repository.UserRepository
import com.example.parkingdev01.util.PreferencesManager

class AuthViewModel(
    private val userRepository: UserRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    suspend fun login(email: String, password: String): Boolean {
        val isSuccess = userRepository.authenticate(email, password)

        if (isSuccess) {
            val user: User? = userRepository.getDetails(email, password)
            user?.let { preferencesManager.saveUser(it) }
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
        val isSuccess = userRepository.createUser(
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
        }

        return isSuccess
    }
}
