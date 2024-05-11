package com.example.parkingdev01.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.repository.UserRepository


class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun login(email: String, password: String): Boolean {
        return userRepository.authenticate(email, password)
    }

    suspend fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUrl: String
    ): Boolean {

        return userRepository.createUser(
            email,
            password,
            firstName,
            lastName,
            phoneNumber,
            photoUrl
        )
    }
}